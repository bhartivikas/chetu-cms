package com.clientmanagement.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientmanagement.app.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

}
