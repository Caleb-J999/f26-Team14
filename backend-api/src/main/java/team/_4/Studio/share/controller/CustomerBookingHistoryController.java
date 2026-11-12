package team._4.Studio.share.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import team._4.Studio.share.Booking;
import team._4.Studio.share.BookingService;

@Controller
public class CustomerBookingHistoryController {

    private final BookingService bookingService;

    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("MMM d, yyyy 'at' h:mm a");

    public CustomerBookingHistoryController(
            BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/my-bookings")
    @Transactional(readOnly = true)
    public String showMyBookings(
            HttpServletRequest request,
            Model model) {

        HttpSession session = request.getSession(false);

        if (session == null
                || !(session.getAttribute("customerId") instanceof Long)) {
            return "redirect:/login";
        }

        Long customerId = (Long) session.getAttribute("customerId");

        List<Booking> bookings = bookingService
                .getBookingsByCustomer(customerId)
                .stream()
                .sorted(Comparator.comparing(Booking::getStartTime))
                .toList();

        List<Map<String, Object>> upcomingBookings = new ArrayList<>();
        List<Map<String, Object>> pastBookings = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : bookings) {
            Map<String, Object> details = new LinkedHashMap<>();

            details.put("id", booking.getBookingId());
            details.put(
                    "title",
                    booking.getService() == null
                            ? "Space unavailable"
                            : booking.getService().getTitle());
            details.put(
                    "startTime",
                    booking.getStartTime().format(DISPLAY_TIME));
            details.put(
                    "endTime",
                    booking.getEndTime().format(DISPLAY_TIME));
            details.put(
                    "status",
                    booking.getStatus() == null
                            ? "UNKNOWN"
                            : booking.getStatus());
            details.put(
                    "total",
                    booking.getTotalPrice() == null
                            ? "Not recorded"
                            : "$" + booking.getTotalPrice().toPlainString());

            if (booking.getEndTime().isAfter(now)) {
                upcomingBookings.add(details);
            } else {
                pastBookings.add(details);
            }
        }

        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("pastBookings", pastBookings);

        return "my-bookings";
    }
}