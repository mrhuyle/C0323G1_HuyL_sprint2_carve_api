package com.example.carve.order.dto;

import java.sql.Timestamp;

public interface OrderListDTO {
    String getCode();

    float getDiscount();

    Long getTotal();

    boolean getPaid();

    Timestamp getCreatedTime();

    String getInvoice();

    String getUsername();
}
