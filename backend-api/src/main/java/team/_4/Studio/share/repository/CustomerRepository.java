package team._4.Studio.share.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team._4.Studio.share.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}