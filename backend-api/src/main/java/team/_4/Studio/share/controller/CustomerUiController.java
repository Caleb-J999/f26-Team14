package team._4.Studio.share.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import team._4.Studio.share.Service;
import team._4.Studio.share.StudioListingService;
import team._4.Studio.share.entity.Customer;
import team._4.Studio.share.service.CustomerService;

@Controller
public class CustomerUiController {

    private final CustomerService customerService;
    private final StudioListingService studioListingService;

    public CustomerUiController(
            CustomerService customerService,
            StudioListingService studioListingService) {

        this.customerService = customerService;
        this.studioListingService = studioListingService;
    }

    @InitBinder("customer")
    public void configureCustomerBinding(WebDataBinder binder) {
        binder.setAllowedFields(
                "firstName", "lastName", "email", "phone");
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerCustomer(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult,
            @RequestParam(name = "password", defaultValue = "")
                    String password,
            @RequestParam(name = "confirmPassword", defaultValue = "")
                    String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute(
                    "errorMessage", "Passwords do not match.");
            return "signup";
        }

        try {
            customerService.registerCustomer(customer, password);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "signup";
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute(
                    "errorMessage",
                    "We could not save your registration. "
                            + "Please check your details or use another email.");
            return "signup";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Registration saved. Please log in.");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "email", defaultValue = "")
                    String email,
            @RequestParam(name = "password", defaultValue = "")
                    String password,
            HttpServletRequest request,
            Model model) {

        Optional<Customer> customer =
                customerService.authenticateCustomer(email, password);

        if (customer.isEmpty()) {
            model.addAttribute(
                    "errorMessage", "Invalid email or password.");
            model.addAttribute("email", email);
            return "login";
        }

        HttpSession oldSession = request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(
                "customerId", customer.get().getCustomerId());

        return "redirect:/spaces";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

    @GetMapping("/spaces")
    public String browseSpaces(
            @RequestParam(name = "query", defaultValue = "")
                    String query,
            Model model) {

        String search = query.trim().toLowerCase(Locale.ROOT);

        List<Service> spaces = studioListingService.getAllServices()
                .stream()
                .filter(space ->
                        Boolean.TRUE.equals(space.getIsActive()))
                .filter(space -> search.isEmpty()
                        || (space.getTitle() != null
                            && space.getTitle()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(search))
                        || (space.getLocation() != null
                            && space.getLocation()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(search)))
                .toList();

        model.addAttribute("spaces", spaces);
        model.addAttribute("query", query);

        return "spaces";
    }
}