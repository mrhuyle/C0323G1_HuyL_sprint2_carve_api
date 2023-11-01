package com.example.carve.deck.service;

import com.example.carve.deck.controller.CreateDeckRequest;
import com.example.carve.home.dto.DeckDTO;

public interface IDeckService {
    boolean createDeck(CreateDeckRequest request);

    DeckDTO getDeck(Long id);

    boolean editDeck(CreateDeckRequest request, Long id);
}
