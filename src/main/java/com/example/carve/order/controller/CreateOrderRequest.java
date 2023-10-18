package com.example.carve.order.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private float discount;
    private Long total;
    private Long cartId;
    private List<Long> listCartItemIds;
}
