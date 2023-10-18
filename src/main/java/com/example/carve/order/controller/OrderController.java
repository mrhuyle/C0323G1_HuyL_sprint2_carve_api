package com.example.carve.order.controller;

import com.example.carve.order.dto.OrderItemDTO;
import com.example.carve.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        Long orderId = orderService.createOrder(request);
        if (orderId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
