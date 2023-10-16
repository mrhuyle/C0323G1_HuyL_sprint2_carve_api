package com.example.carve.cart.model.dto;

public interface CartItemDTO {
    Long getUserId();
    String getUsername();
    Long getCartId();
    Long getCartItemId();
    Long getDeckId();
    String getDeckName();
    String getDeckImg();
    Long getDeckPrice();
    float getPromoPercent();
    float getDiscount();
}
