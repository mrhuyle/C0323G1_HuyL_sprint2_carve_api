package com.example.carve.deck.service;

import com.example.carve.deck.controller.CreateDeckRequest;
import com.example.carve.deck.model.Deck;
import com.example.carve.deck.model.Tag;
import com.example.carve.deck.repository.DeckRepository;
import com.example.carve.deck.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeckService implements IDeckService {
    private final DeckRepository deckRepository;
    private final TagRepository tagRepository;

    @Override
    public boolean createDeck(CreateDeckRequest request) {
        var newDeck = Deck.builder()
                .name(request.getName())
                .description(request.getDescription())
                .img(request.getImg())
                .price(request.getPrice())
                .promoPercent(request.getPromoPercent())
                .createdTime(Timestamp.from(Instant.now()))
                .isProduct(true)
                .build();
        Set<Tag> tags = new HashSet<>();
        for (String tagName : request.getTagName()) {
            Tag tag = tagRepository.findByName(tagName); // Implement tagService.findById() to retrieve a Tag by its ID
            if (tag != null) {
                tags.add(tag);
            } else {
                var newTag = Tag.builder().name(tagName).build();
                tags.add(newTag);
            }
        }
        newDeck.setTags(tags);
        deckRepository.save(newDeck);
        return true;
    }
}
