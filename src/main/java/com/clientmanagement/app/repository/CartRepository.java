package com.clientmanagement.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientmanagement.app.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUsername(String username);

}
