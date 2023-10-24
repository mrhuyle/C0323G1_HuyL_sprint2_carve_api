package com.example.carve.order.model;

import com.example.carve.cart.model.Cart;
import com.example.carve.cart.model.CartItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private float discount;

    private Long total;

    private boolean paid;

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp createdTime;

    @Column(columnDefinition = "VARCHAR (2084)")
    private String invoice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "order")
    private Set<CartItem> cartItems = new HashSet<>();
}
