package com.example.carve.home.service;

import com.example.carve.home.dto.DeckForHomePageDTO;
import com.example.carve.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService implements IHomeService {

    private final HomeRepository homeRepository;

    @Override
    public List<DeckForHomePageDTO> findLatestDecksForHomePage(String keyword, String tag) {
        String keywordParam = '%' + keyword + '%';
        String tagParam = '%' + tag + '%';
        return homeRepository.findLatestDecksForHomePage(keywordParam, tagParam);
    }

    @Override
    public DeckForHomePageDTO getDeckDetail(Long id) {
        return homeRepository.getDeckDetail(id);
    }
}
