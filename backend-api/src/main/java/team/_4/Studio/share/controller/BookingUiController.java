package team._4.Studio.share.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import team._4.Studio.share.Booking;
import team._4.Studio.share.BookingService;
import team._4.Studio.share.Service;
import team._4.Studio.share.StudioListingService;

@Controller
public class BookingUiController {

    private final BookingService bookingService;
    private final StudioListingService studioListingService;

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
            HttpServletResponse response) {

        Service space = findActiveSpace(id);

        if (space == null) {
            response.setStatus(404);
            model.addAttribute(
                    "errorMessage",
                    "This space was not found or is no longer available.");
            return "customer-error";
        }

        model.addAttribute("space", space);
        return "book-space";
    }

    @PostMapping("/spaces/{id}/book")
    public String submitBooking(
            @PathVariable("id") String id,
            @RequestParam(name = "customerId", defaultValue = "")
                    String customerId,
            @RequestParam(name = "startTime", defaultValue = "")
                    String startTime,
            @RequestParam(name = "endTime", defaultValue = "")
                    String endTime,
            Model model,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        Service space = findActiveSpace(id);

        if (space == null) {
            response.setStatus(404);
            model.addAttribute(
                    "errorMessage",
                    "This space was not found or is no longer available.");
            return "customer-error";
        }

        model.addAttribute("space", space);
        model.addAttribute("customerId", customerId);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        try {
            Booking booking = bookingService.createCustomerBooking(
                    space.getServiceId(),
                    Long.valueOf(customerId.trim()),
                    LocalDateTime.parse(startTime),
                    LocalDateTime.parse(endTime));

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Booking #" + booking.getBookingId()
                            + " was saved. Status: PENDING. Total: $"
                            + booking.getTotalPrice().toPlainString());

            return "redirect:/spaces/" + space.getServiceId() + "/book";

        } catch (NumberFormatException exception) {
            model.addAttribute(
                    "errorMessage",
                    "Enter a valid numeric customer ID.");

        } catch (DateTimeParseException exception) {
            model.addAttribute(
                    "errorMessage",
                    "Enter valid start and end dates and times.");

        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
        }

        return "book-space";
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