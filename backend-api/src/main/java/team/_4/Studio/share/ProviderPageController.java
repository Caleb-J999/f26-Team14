package team._4.Studio.share;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/provider")
public class ProviderPageController {

    private final StudioListingService listingService;

    public ProviderPageController(StudioListingService listingService) {
        this.listingService = listingService;
    }

    // PROVIDER DASHBOARD
    @GetMapping("/dashboard")
    public String showProviderDashboard() {
        return "provider-dashboard";
    }

    // MY LISTINGS PAGE
    @GetMapping("/listings")
    public String showProviderListings(Model model) {
        model.addAttribute("listings", listingService.getAllServices());
        return "provider-listings";
    }

    // CREATE LISTING PAGE
    @GetMapping("/listings/create")
    public String showCreateListingPage() {
        return "create-listing";
    }

    // CREATE LISTING FORM SUBMISSION
    @PostMapping("/listings/create")
    public String createListing(
            @RequestParam Long providerId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String location,
            @RequestParam BigDecimal pricePerHour,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String imageUrl,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (title.isBlank() || description.isBlank()) {
            model.addAttribute("error",
                    "Space name and description are required.");
            return "create-listing";
        }

        if (pricePerHour.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("error",
                    "Price per hour must be greater than zero.");
            return "create-listing";
        }

        if (capacity != null && capacity < 1) {
            model.addAttribute("error",
                    "Capacity must be at least 1.");
            return "create-listing";
        }

        Service listing = new Service();
        listing.setTitle(title);
        listing.setDescription(description);
        listing.setLocation(location);
        listing.setPricePerHour(pricePerHour);
        listing.setCapacity(capacity);
        listing.setImageUrl(imageUrl);

        return listingService.createService(providerId, listing)
                .map(savedListing -> {
                    redirectAttributes.addFlashAttribute(
                            "success", "Listing created successfully!");
                    return "redirect:/provider/listings/create";
                })
                .orElseGet(() -> {
                    model.addAttribute("error",
                            "Provider not found. Please enter a valid Provider ID.");
                    return "create-listing";
                });
    }

    // EDIT LISTING PAGE
    @GetMapping("/listings/{id}/edit")
    public String showEditListingPage(
            @PathVariable Long id,
            Model model) {

        return listingService.getServiceById(id)
                .map(listing -> {
                    model.addAttribute("listing", listing);
                    return "edit-listing";
                })
                .orElseGet(() -> {
                    model.addAttribute("error",
                            "Listing not found. Please enter a valid Listing ID.");
                    return "listing-not-found";
                });
    }

    // EDIT LISTING FORM SUBMISSION
    @PostMapping("/listings/{id}/edit")
    public String updateListing(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String location,
            @RequestParam BigDecimal pricePerHour,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String imageUrl,
            Model model,
            RedirectAttributes redirectAttributes) {

        Service updatedListing = new Service();

        updatedListing.setServiceId(id);
        updatedListing.setTitle(title);
        updatedListing.setDescription(description);
        updatedListing.setLocation(location);
        updatedListing.setPricePerHour(pricePerHour);
        updatedListing.setCapacity(capacity);
        updatedListing.setImageUrl(imageUrl);

        if (title.isBlank() || description.isBlank()) {
            model.addAttribute("error",
                    "Space name and description are required.");
            model.addAttribute("listing", updatedListing);
            return "edit-listing";
        }

        if (pricePerHour.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("error",
                    "Price per hour must be greater than zero.");
            model.addAttribute("listing", updatedListing);
            return "edit-listing";
        }

        if (capacity != null && capacity < 1) {
            model.addAttribute("error",
                    "Capacity must be at least 1.");
            model.addAttribute("listing", updatedListing);
            return "edit-listing";
        }

        return listingService.updateService(id, updatedListing)
                .map(savedListing -> {
                    redirectAttributes.addFlashAttribute(
                            "success", "Listing updated successfully!");
                    return "redirect:/provider/listings/" + id + "/edit";
                })
                .orElseGet(() -> {
                    model.addAttribute("error",
                            "Listing not found. Please enter a valid Listing ID.");
                    return "listing-not-found";
                });
    }
}