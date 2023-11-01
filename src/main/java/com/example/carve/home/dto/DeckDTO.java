package com.example.carve.home.dto;

import java.sql.Timestamp;

public interface DeckDTO {
    Long getId();

    String getName();

    String getDescription();

    Long getPrice();

    float getPromoPercent();

    String getImg();

    Long getCardQuantity();

    String getTagName();

    Timestamp getCreatedTime();
}
