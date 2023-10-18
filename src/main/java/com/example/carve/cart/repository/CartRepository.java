package com.example.carve.cart.repository;

import com.example.carve.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value = " SELECT c.id FROM cart c LEFT JOIN user u ON c.user_id = u.id WHERE u.username = :username", nativeQuery = true)
    Long getCartIdByUsername(@Param("username") String username);
}
