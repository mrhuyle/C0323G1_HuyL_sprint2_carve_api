package com.example.carve.home.service;

import com.example.carve.home.dto.DeckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHomeService {
    List<DeckDTO> findLatestDecksForHomePage(String keyword, String tag);

    DeckDTO getDeckDetail(Long id);

    Page<DeckDTO> getListWithPagination(String keyword, Pageable pageable);
}
