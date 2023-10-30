package com.example.carve.order.repository;

import com.example.carve.order.dto.OrderListDTO;
import com.example.carve.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o.code AS code, o.discount AS discount, o.total AS total, o.paid AS paid, o.created_time AS createdTime, o.invoice AS invoice, u.username AS username " +
            "FROM _order o " +
            "INNER JOIN cart c ON o.cart_id = c.id " +
            "INNER JOIN user u ON c.user_id = u.id " +
            "ORDER BY o.created_time DESC ", nativeQuery = true)
    List<OrderListDTO> findOrders();
}
