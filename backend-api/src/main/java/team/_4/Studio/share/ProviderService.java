
package team._4.Studio.share;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final ProviderRepository repository;

    public ProviderService(ProviderRepository repository) {
        this.repository = repository;
    }

    public List<Provider> getAllProviders() {
        return repository.findAll();
    }

    public Optional<Provider> getProviderById(Long id) {
        return repository.findById(id);
    }

    public Provider createProvider(Provider provider) {
        return repository.save(provider);
    }

    public Optional<Provider> updateProvider(Long id, Provider updatedProvider) {
        return repository.findById(id).map(provider -> {
            provider.setFirstName(updatedProvider.getFirstName());
            provider.setLastName(updatedProvider.getLastName());
            provider.setEmail(updatedProvider.getEmail());
            provider.setPhone(updatedProvider.getPhone());
            provider.setBio(updatedProvider.getBio());
            provider.setProfileImageUrl(updatedProvider.getProfileImageUrl());
            provider.setIsActive(updatedProvider.getIsActive());
            provider.setUpdatedAt(java.time.LocalDateTime.now());
            return repository.save(provider);
        });
    }
}