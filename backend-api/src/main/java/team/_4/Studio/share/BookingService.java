
package team._4.Studio.share;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return repository.findById(id);
    }

    // Get bookings by provider
    public List<Booking> getBookingsByProvider(Long providerId) {
        return repository.findByProvider_ProviderId(providerId);
    }

    // Get bookings by studio listing
    public List<Booking> getBookingsByService(Long serviceId) {
        return repository.findByService_ServiceId(serviceId);
    }

    // Create a new booking
    public Booking createBooking(Booking booking) {
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        return repository.save(booking);
    }

    // Update booking status
    public Optional<Booking> updateBookingStatus(Long id, String status) {
        return repository.findById(id).map(booking -> {
            booking.setStatus(status);
            booking.setUpdatedAt(LocalDateTime.now());
            return repository.save(booking);
        });
    }

    // Cancel a booking
    public boolean cancelBooking(Long id) {
        Optional<Booking> existingBooking = repository.findById(id);

        if (existingBooking.isEmpty()) {
            return false;
        }

        Booking booking = existingBooking.get();
        booking.setStatus("CANCELLED");
        booking.setUpdatedAt(LocalDateTime.now());
        repository.save(booking);

        return true;
    }

    // Get bookings by customer
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}