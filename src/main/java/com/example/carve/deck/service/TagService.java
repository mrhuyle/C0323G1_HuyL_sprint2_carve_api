package com.example.carve.deck.service;

import com.example.carve.deck.model.Tag;
import com.example.carve.deck.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService{
    private final TagRepository tagRepository;

    @Override
    public List<String> findTagsByDeckId(Long id) {
        return tagRepository.findTagsByDeckId(id);
    }
}
