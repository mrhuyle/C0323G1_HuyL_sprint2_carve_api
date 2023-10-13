package com.example.carve.deck.model;

import com.example.carve.cart.model.CartItem;
import com.example.carve.user.model.User;
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
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long price;

    private float promoPercent;

    @Column(columnDefinition = "TEXT")
    private String img;

    private boolean isProduct;

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp createdTime;

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedTime;

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isDeleted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "deck_tag",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "deck")
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToMany(mappedBy = "decks")
    private Set<User> users;

}
