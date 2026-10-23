package team._4.Studio.share;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByProvider_ProviderId(Long providerId);

    List<Booking> findByService_ServiceId(Long serviceId);

    List<Booking> findByCustomerId(Long customerId);
}