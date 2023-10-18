package com.example.carve.cart.service;

import com.example.carve.cart.controller.CartItemRequest;
import com.example.carve.cart.model.CartItem;
import com.example.carve.cart.dto.CartItemDTO;
import com.example.carve.user.model.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICartService {
    void createCart(User user);

    List<CartItemDTO> getCartItemsByUsername(String username);

    void deleteCartItemById(Long id);

    void addCartItem(CartItemRequest request);

    CartItem getCartItemsByCartIdAndDeckIdAndNotBought(Long cartId, Long deckId);

    Long getCartIdByUsername(String username);

    List<CartItemDTO> getCartItemsByOrder(@RequestParam Long orderId);

}
