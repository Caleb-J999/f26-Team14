
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

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return repository.findById(id);
    }

    public List<Booking> getBookingsByProvider(Long providerId) {
        return repository.findByProvider_ProviderId(providerId);
    }

    public List<Booking> getBookingsByService(Long serviceId) {
        return repository.findByService_ServiceId(serviceId);
    }

    public Optional<Booking> updateBookingStatus(Long id, String status) {
        return repository.findById(id).map(booking -> {
            booking.setStatus(status);
            booking.setUpdatedAt(LocalDateTime.now());
            return repository.save(booking);
        });
    }

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
}