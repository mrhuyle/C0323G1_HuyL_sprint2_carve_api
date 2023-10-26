package com.example.carve.deck.controller;

import com.example.carve.cart.model.CartItem;
import com.example.carve.deck.model.Tag;
import com.example.carve.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeckRequest {
    private String name;
    private String description;
    private Long price;
    private float promoPercent;
    private String img;
    private List<String> tagName;
}
