package team._4.Studio.share.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import team._4.Studio.share.entity.Customer;
import team._4.Studio.share.service.CustomerService;

@Controller
public class CustomerUiController {

    private final CustomerService customerService;

    public CustomerUiController(CustomerService customerService) {
        this.customerService = customerService;
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
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "errors", bindingResult.getAllErrors());
            return "signup";
        }

        try {
            customerService.createCustomer(customer);
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute(
                    "errorMessage",
                    "We could not save your registration. "
                            + "Please check your details or use another email.");
            return "signup";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Your customer registration was saved successfully.");

        return "redirect:/signup";
    }
}