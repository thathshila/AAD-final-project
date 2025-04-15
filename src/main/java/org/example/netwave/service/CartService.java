package org.example.netwave.service;


import org.example.netwave.dto.CartItemRequest;
import org.example.netwave.dto.CartItemResponse;
import org.example.netwave.entity.Cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartItems(String jwtToken) throws Exception;

    CartItemResponse addItemToCart(String userId, CartItemRequest cartItemRequest);
}