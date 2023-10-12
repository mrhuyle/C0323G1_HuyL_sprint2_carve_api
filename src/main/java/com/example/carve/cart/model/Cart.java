package com.example.carve.cart.model;

import com.example.carve.deck.model.Deck;
import com.example.carve.order.model.Order;
import com.example.carve.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartDecks = new HashSet<>();

    @OneToMany(mappedBy = "cart")
    private Set<Order> orders = new HashSet<>();
}
