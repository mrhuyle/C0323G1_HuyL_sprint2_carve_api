package com.example.carve.order.service;

import com.example.carve.cart.model.CartItem;
import com.example.carve.cart.repository.CartItemRepository;
import com.example.carve.cart.repository.CartRepository;
import com.example.carve.order.controller.CreateOrderRequest;
import com.example.carve.order.controller.SaveInvoiceRequest;
import com.example.carve.order.model.Order;
import com.example.carve.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        String code = generateOrderCode(savedOrder.getId());
        savedOrder.setCode(code);

        return orderRepository.save(savedOrder);
    }

    private String generateOrderCode(Long id) {
        String formattedId = String.format("%04d", id);
        return "HD-" + formattedId;
    }

    @Override
    public Long createOrder(CreateOrderRequest request) {
        var newOrder = Order.builder()
                .cart(cartRepository.findById(request.getCartId()).get())
                .paid(false)
                .discount(request.getDiscount())
                .total(request.getTotal())
                .createdTime(Timestamp.from(Instant.now()))
                .build();
        var savedOrder = saveOrder(newOrder); //set format code here
        List<Long> listCartItemIds = request.getListCartItemIds();
        for (Long itemId : listCartItemIds) { //set cart item belongs to order
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setOrder(savedOrder);
                cartItem.setBought(true);
                cartItemRepository.save(cartItem);
            }
        }
        return savedOrder.getId();
    }

    @Override
    public boolean saveInvoice(SaveInvoiceRequest request) {
        var order = orderRepository.findById(request.getOrderId());
        if (order.isPresent()) {
            order.get().setInvoice(request.getInvoiceLink());
            return true;
        }
        return false;
    }
}
