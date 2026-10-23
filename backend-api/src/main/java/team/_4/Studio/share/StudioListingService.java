
package team._4.Studio.share;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class StudioListingService {

    private final ServiceRepository repository;
    private final ProviderRepository providerRepository;

    public StudioListingService(ServiceRepository repository,
                                ProviderRepository providerRepository) {
        this.repository = repository;
        this.providerRepository = providerRepository;
    }

    public List<Service> getAllServices() {
        return repository.findAll();
    }

    public Optional<Service> getServiceById(Long id) {
        return repository.findById(id);
    }

    public Optional<Service> createService(Long providerId, Service service) {
        return providerRepository.findById(providerId).map(provider -> {
            service.setProvider(provider);
            service.setIsActive(true);
            service.setCreatedAt(LocalDateTime.now());
            service.setUpdatedAt(LocalDateTime.now());
            return repository.save(service);
        });
    }

    public Optional<Service> updateService(Long id, Service updatedService) {
        return repository.findById(id).map(service -> {
            service.setTitle(updatedService.getTitle());
            service.setDescription(updatedService.getDescription());
            service.setLocation(updatedService.getLocation());
            service.setPricePerHour(updatedService.getPricePerHour());
            service.setCapacity(updatedService.getCapacity());
            service.setImageUrl(updatedService.getImageUrl());
            service.setUpdatedAt(LocalDateTime.now());
            return repository.save(service);
        });
    }

    public boolean deactivateService(Long id) {
        Optional<Service> existingService = repository.findById(id);

        if (existingService.isEmpty()) {
            return false;
        }

        Service service = existingService.get();
        service.setIsActive(false);
        service.setUpdatedAt(LocalDateTime.now());
        repository.save(service);

        return true;
    }
}