package com.example.carve.cart.controller;

import com.example.carve.cart.dto.CartItemDTO;
import com.example.carve.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class CartController {
    private final CartService cartService;

    @GetMapping("/get-items")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUsername(@RequestParam String username) {
        List<CartItemDTO> items = cartService.getCartItemsByUsername(username);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/delete-item")
    public ResponseEntity<?> deleteCartItemById(@RequestParam Long id) {
        try {
            cartService.deleteCartItemById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemRequest request) {
        if (cartService.getCartItemsByCartIdAndDeckIdAndNotBought(request.getCartId(), request.getDeckId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        cartService.addCartItem(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-id")
    public ResponseEntity<Long> getCartIdByUsername(@RequestParam String username) {
        if (cartService.getCartIdByUsername(username) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.getCartIdByUsername(username));
    }

    @GetMapping("/get-order")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByOrder(@RequestParam Long id) {
        List<CartItemDTO> items = cartService.getCartItemsByOrder(id);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
