package com.example.carve.order.service;

import com.example.carve.order.controller.CreateOrderRequest;
import com.example.carve.order.controller.SaveInvoiceRequest;
import com.example.carve.order.dto.OrderListDTO;

import java.util.List;

public interface IOrderService {
    public Long createOrder(CreateOrderRequest request);

    public boolean saveInvoice(SaveInvoiceRequest request);

    List<OrderListDTO> findOrders();

    boolean setOrderToBought(Long id);
}
