package team._4.Studio.share;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceAvailabilityRepository
        extends JpaRepository<SpaceAvailability, Long> {

    List<SpaceAvailability>
            findByService_ServiceIdOrderByStartTimeAsc(Long serviceId);

    boolean existsByService_ServiceIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long serviceId,
            LocalDateTime requestedStart,
            LocalDateTime requestedEnd);
}