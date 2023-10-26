package com.example.carve.deck.service;

import com.example.carve.deck.model.Tag;

import java.util.List;

public interface ITagService {
    List<String> findTagsByDeckId(Long id);
}
