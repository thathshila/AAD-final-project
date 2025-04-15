package org.example.netwave.repo;


import org.example.netwave.entity.Cart;
import org.example.netwave.entity.CartItem;
import org.example.netwave.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndItemAndColorAndStorage(
            Cart cart, Item item, String color, String storage);
}