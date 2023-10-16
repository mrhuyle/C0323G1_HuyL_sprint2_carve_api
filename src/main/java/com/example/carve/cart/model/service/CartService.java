package com.example.carve.cart.model.service;

import com.example.carve.cart.model.Cart;
import com.example.carve.cart.model.dto.CartItemDTO;
import com.example.carve.cart.model.repository.CartItemRepository;
import com.example.carve.cart.model.repository.CartRepository;
import com.example.carve.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    public void createCart(User user) {
        var newCart = Cart.builder()
                .user(user)
                .build();
        log.info("Also saving cart of user {} to the database", user.getName());
        cartRepository.save(newCart);
    }

    @Override
    public List<CartItemDTO> getCartItemsByUsername(String username) {
        return cartItemRepository.findCartItemsByUsername(username);
    }
}
