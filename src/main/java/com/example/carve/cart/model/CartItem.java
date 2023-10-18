package com.example.carve.cart.model;

import com.example.carve.deck.model.Deck;
import com.example.carve.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long price;

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isBought;
}
