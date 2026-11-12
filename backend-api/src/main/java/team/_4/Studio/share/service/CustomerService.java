package team._4.Studio.share.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import team._4.Studio.share.entity.Customer;
import team._4.Studio.share.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer registerCustomer(Customer customer, String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters.");
        }

        if (password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException(
                    "Password is too long. Use at most 72 UTF-8 bytes.");
        }

        if (customer.getEmail() == null
                || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required.");
        }

        String email = customer.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (customerRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "That email is already registered. "
                            + "Please use another email or log in.");
        }

        customer.setEmail(email);
        customer.setPasswordHash(passwordEncoder.encode(password));

        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> authenticateCustomer(
            String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isEmpty()
                || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            return Optional.empty();
        }

        return customerRepository.findByEmailIgnoreCase(email.trim())
                .filter(customer ->
                        customer.getPasswordHash() != null
                        && passwordEncoder.matches(
                                password, customer.getPasswordHash()));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with ID: " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(id);

        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setProfileImageUrl(
                updatedCustomer.getProfileImageUrl());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
}