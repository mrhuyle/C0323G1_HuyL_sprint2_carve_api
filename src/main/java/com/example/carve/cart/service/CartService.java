package com.example.carve.cart.service;

import com.example.carve.cart.controller.CartItemRequest;
import com.example.carve.cart.model.Cart;
import com.example.carve.cart.model.CartItem;
import com.example.carve.cart.dto.CartItemDTO;
import com.example.carve.cart.repository.CartItemRepository;
import com.example.carve.cart.repository.CartRepository;
import com.example.carve.deck.repository.DeckRepository;
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
    private final DeckRepository deckRepository;

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

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void addCartItem(CartItemRequest request) {
        var cartItem = CartItem.builder()
                .price(deckRepository.findById(request.getDeckId()).get().getPrice())
                .cart(cartRepository.findById(request.getCartId()).get())
                .deck(deckRepository.findById(request.getDeckId()).get())
                .isBought(false)
                .build();
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItemsByCartIdAndDeckIdAndNotBought(Long cartId, Long deckId) {
        return cartItemRepository.findCartItemsByCartIdAndDeckIdAndNotBought(cartId, deckId);
    }

    @Override
    public Long getCartIdByUsername(String username) {
        return cartRepository.getCartIdByUsername(username);
    }

    @Override
    public List<CartItemDTO> getCartItemsByOrder(Long orderId) {
        return cartItemRepository.findCartItemsByOrder(orderId);
    }
}
