package com.example.carve.cart.repository;

import com.example.carve.cart.model.CartItem;
import com.example.carve.cart.dto.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
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

    @Query(value = "SELECT ci.* " +
            " FROM cart_item ci " +
            " WHERE ci.cart_id = :cartId AND ci.deck_id = :deckId AND ci.is_bought = false", nativeQuery = true)
    CartItem findCartItemsByCartIdAndDeckIdAndNotBought(@Param("cartId") Long cartId, @Param("deckId") Long deckId);


    @Query(value = " SELECT\n" +
            "    u.id AS userId,\n" +
            "    u.username,\n" +
            "    ci.cart_id AS cartId,\n" +
            "    ci.id AS cartItemId,\n" +
            "    ci.deck_id AS deckId,\n" +
            "    d.name AS deckName,\n" +
            "    d.img AS deckImg,\n" +
            "    d.price AS deckPrice,\n" +
            "    d.promo_percent AS promoPercent,\n" +
            "    o.code AS orderCode, " +
            "    o.created_time AS createdTime, " +
            "    r.discount " +
            "FROM\n" +
            "    cart_item ci\n" +
            "INNER JOIN deck d ON ci.deck_id = d.id\n" +
            "INNER JOIN cart c ON ci.cart_id = c.id\n" +
            "INNER JOIN user u ON c.user_id = u.id\n" +
            "INNER JOIN _order o ON ci.order_id = o.id\n" +
            "INNER JOIN rank_user r ON u.rank_id = r.id\n" +
            "WHERE\n" +
            "    ci.is_bought = 1\n" +
            "    AND o.id = :orderId ; ", nativeQuery = true)
    List<CartItemDTO> findCartItemsByOrder(@Param("orderId") Long orderId);
}
