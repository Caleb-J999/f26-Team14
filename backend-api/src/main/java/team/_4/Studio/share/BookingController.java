package team._4.Studio.share;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // Get all bookings or filter by customer, provider, or service
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) Long serviceId) {

        if (customerId != null) {
            return ResponseEntity.ok(
                    service.getBookingsByCustomer(customerId));
        }

        if (providerId != null) {
            return ResponseEntity.ok(
                    service.getBookingsByProvider(providerId));
        }

        if (serviceId != null) {
            return ResponseEntity.ok(
                    service.getBookingsByService(serviceId));
        }

        return ResponseEntity.ok(service.getAllBookings());
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(
            @PathVariable Long id) {

        return service.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @Valid @RequestBody Booking booking) {

        Booking savedBooking = service.createBooking(booking);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBooking);
    }

    // Update booking status
    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updateBookingStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cancel a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long id) {

        if (!service.cancelBooking(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}