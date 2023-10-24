package com.example.carve.order.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveInvoiceRequest {
    private Long orderId;
    private String invoiceLink;
}
