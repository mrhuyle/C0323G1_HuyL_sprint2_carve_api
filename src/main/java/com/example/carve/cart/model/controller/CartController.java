package com.example.carve.cart.model.controller;

import com.example.carve.cart.model.dto.CartItemDTO;
import com.example.carve.cart.model.service.CartService;
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
}
