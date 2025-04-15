package org.example.netwave.repo;


import org.example.netwave.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
}