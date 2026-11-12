package team._4.Studio.share;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import team._4.Studio.share.entity.Customer;

@Service
public class BookingService {

    private final BookingRepository repository;
    private final SpaceAvailabilityRepository availabilityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BookingService(
            BookingRepository repository,
            SpaceAvailabilityRepository availabilityRepository) {

        this.repository = repository;
        this.availabilityRepository = availabilityRepository;
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

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<SpaceAvailability> getAvailabilityByService(Long serviceId) {
        return availabilityRepository
                .findByService_ServiceIdOrderByStartTimeAsc(serviceId);
    }

    // Existing REST creation behavior is preserved.
    public Booking createBooking(Booking booking) {
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        return repository.save(booking);
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

    @Transactional
    public Booking createCustomerBooking(
            Long serviceId,
            Long customerId,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (serviceId == null || customerId == null) {
            throw new IllegalArgumentException(
                    "Please select a space and log in.");
        }

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Start and end times are required.");
        }

        if (!startTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "The booking must start in the future.");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "The end time must be after the start time.");
        }

        Customer customer = entityManager.find(
                Customer.class, customerId);

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer not found. Please register and log in.");
        }

        team._4.Studio.share.Service space = entityManager.find(
                team._4.Studio.share.Service.class,
                serviceId,
                LockModeType.PESSIMISTIC_WRITE);

        if (space == null || !Boolean.TRUE.equals(space.getIsActive())) {
            throw new IllegalArgumentException(
                    "This space was not found or is no longer available.");
        }

        boolean withinAvailability = availabilityRepository
                .existsByService_ServiceIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        serviceId, startTime, endTime);

        if (!withinAvailability) {
            throw new IllegalArgumentException(
                    "Choose a start and end time entirely within "
                            + "one of the offered availability windows.");
        }

        boolean overlaps = repository.findByService_ServiceId(serviceId)
                .stream()
                .filter(existing ->
                        !"CANCELLED".equalsIgnoreCase(existing.getStatus())
                        && !"REJECTED".equalsIgnoreCase(existing.getStatus()))
                .anyMatch(existing ->
                        existing.getStartTime().isBefore(endTime)
                        && existing.getEndTime().isAfter(startTime));

        if (overlaps) {
            throw new IllegalArgumentException(
                    "This space is already booked during that time. "
                            + "Please choose another time.");
        }

        if (space.getPricePerHour() == null
                || space.getPricePerHour().signum() < 0) {
            throw new IllegalArgumentException(
                    "This space does not have a valid hourly price.");
        }

        long seconds = Duration.between(startTime, endTime).getSeconds();

        BigDecimal totalPrice = space.getPricePerHour()
                .multiply(BigDecimal.valueOf(seconds))
                .divide(
                        BigDecimal.valueOf(3600),
                        2,
                        RoundingMode.HALF_UP);

        Booking booking = new Booking();
        booking.setService(space);
        booking.setProvider(space.getProvider());
        booking.setCustomerId(customer.getCustomerId());
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalPrice(totalPrice);

        return createBooking(booking);
    }
}