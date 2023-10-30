package com.example.carve.order.controller;

import com.example.carve.order.dto.OrderListDTO;
import com.example.carve.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @PostMapping("/save-invoice")
    public ResponseEntity<?> saveInvoice(@RequestBody SaveInvoiceRequest request) {
        if (orderService.saveInvoice(request)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error save invoice");
        }
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderListDTO>> getOrders () {
        var ordersList = orderService.findOrders();
        if (ordersList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(ordersList);
    }
}
