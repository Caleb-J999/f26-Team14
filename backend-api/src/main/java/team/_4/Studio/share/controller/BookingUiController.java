package team._4.Studio.share.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import team._4.Studio.share.Booking;
import team._4.Studio.share.BookingService;
import team._4.Studio.share.Service;
import team._4.Studio.share.StudioListingService;

@Controller
public class BookingUiController {

    private final BookingService bookingService;
    private final StudioListingService studioListingService;

    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("MMM d, yyyy 'at' h:mm a");

    public BookingUiController(
            BookingService bookingService,
            StudioListingService studioListingService) {

        this.bookingService = bookingService;
        this.studioListingService = studioListingService;
    }

    @GetMapping("/spaces/{id}/book")
    public String bookingForm(
            @PathVariable("id") String id,
            Model model,
            HttpServletResponse response,
            HttpServletRequest request) {

        if (getCustomerId(request) == null) {
            return "redirect:/login";
        }

        Service space = findActiveSpace(id);

        if (space == null) {
            return spaceNotFound(model, response);
        }

        populateBookingPage(space, model);
        return "book-space";
    }

    @PostMapping("/spaces/{id}/book")
    public String submitBooking(
            @PathVariable("id") String id,
            @RequestParam(name = "startTime", defaultValue = "")
                    String startTime,
            @RequestParam(name = "endTime", defaultValue = "")
                    String endTime,
            Model model,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        Long customerId = getCustomerId(request);

        if (customerId == null) {
            return "redirect:/login";
        }

        Service space = findActiveSpace(id);

        if (space == null) {
            return spaceNotFound(model, response);
        }

        populateBookingPage(space, model);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        try {
            Booking booking = bookingService.createCustomerBooking(
                    space.getServiceId(),
                    customerId,
                    LocalDateTime.parse(startTime),
                    LocalDateTime.parse(endTime));

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Booking #" + booking.getBookingId()
                            + " was saved. Status: PENDING. Total: $"
                            + booking.getTotalPrice().toPlainString());

            return "redirect:/spaces/" + space.getServiceId() + "/book";

        } catch (DateTimeParseException exception) {
            model.addAttribute(
                    "errorMessage",
                    "Enter valid start and end dates and times.");

        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
        }

        return "book-space";
    }

    private void populateBookingPage(Service space, Model model) {
        LocalDateTime now = LocalDateTime.now();

        List<Map<String, String>> windows = bookingService
                .getAvailabilityByService(space.getServiceId())
                .stream()
                .filter(window -> window.getEndTime().isAfter(now))
                .map(window -> Map.of(
                        "start", window.getStartTime().format(DISPLAY_TIME),
                        "end", window.getEndTime().format(DISPLAY_TIME)))
                .toList();

        model.addAttribute("space", space);
        model.addAttribute("availabilityWindows", windows);
    }

    private Long getCustomerId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null
                && session.getAttribute("customerId") instanceof Long id) {
            return id;
        }

        return null;
    }

    private String spaceNotFound(
            Model model,
            HttpServletResponse response) {

        response.setStatus(404);
        model.addAttribute(
                "errorMessage",
                "This space was not found or is no longer available.");

        return "customer-error";
    }

    private Service findActiveSpace(String id) {
        try {
            return studioListingService.getServiceById(Long.valueOf(id))
                    .filter(space ->
                            Boolean.TRUE.equals(space.getIsActive()))
                    .orElse(null);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}