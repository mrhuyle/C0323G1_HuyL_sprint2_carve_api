package com.example.carve.cart.model.service;

import com.example.carve.cart.model.dto.CartItemDTO;
import com.example.carve.user.model.User;

import java.util.List;

public interface ICartService {
    void createCart(User user);

    List<CartItemDTO> getCartItemsByUsername(String username);

    void deleteCartItemById(Long id);
}
