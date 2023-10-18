package com.example.carve.order.service;

import com.example.carve.order.controller.CreateOrderRequest;

public interface IOrderService {
    public Long createOrder(CreateOrderRequest request);
}
