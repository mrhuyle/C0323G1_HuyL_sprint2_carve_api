package com.example.carve.home.service;

import com.example.carve.home.dto.DeckForHomePageDTO;

import java.util.List;

public interface IHomeService {
    List<DeckForHomePageDTO> findLatestDecksForHomePage(String keyword, String tag);

    DeckForHomePageDTO getDeckDetail(Long id);
}
