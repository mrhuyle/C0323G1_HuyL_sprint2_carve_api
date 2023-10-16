package com.example.carve.cart.model.repository;

import com.example.carve.cart.model.CartItem;
import com.example.carve.cart.model.dto.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query(value = " SELECT u.id as userId, " +
            "    u.username as username, " +
            "    c.id as cartId, " +
            "    ci.id AS cartItemId, " +
            "    d.id AS deckId, " +
            "    d.name AS deckName, " +
            "    d.img AS deckImg, " +
            "    d.price AS deckPrice, " +
            "    d.promo_percent AS promoPercent, " +
            "    r.discount AS discount " +
            "FROM\n" +
            "    user u\n" +
            "JOIN\n" +
            "    cart c ON u.id = c.user_id\n" +
            "JOIN\n" +
            "    cart_item ci ON c.id = ci.cart_id\n" +
            "JOIN\n" +
            "    deck d ON ci.deck_id = d.id\n" +
            "LEFT JOIN\n" +
            "    rank_user r ON u.rank_id = r.id\n" +
            "WHERE\n" +
            "    u.username = :username " +
            "    AND ci.is_bought = 0 ", nativeQuery = true)
    List<CartItemDTO> findCartItemsByUsername(@Param("username") String username);
}
