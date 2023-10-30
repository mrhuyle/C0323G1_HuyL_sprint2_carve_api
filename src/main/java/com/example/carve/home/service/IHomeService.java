package com.example.carve.home.service;

import com.example.carve.home.dto.DeckForHomePageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHomeService {
    List<DeckForHomePageDTO> findLatestDecksForHomePage(String keyword, String tag);

    DeckForHomePageDTO getDeckDetail(Long id);

    Page<DeckForHomePageDTO> getListWithPagination(String keyword, Pageable pageable);
}
