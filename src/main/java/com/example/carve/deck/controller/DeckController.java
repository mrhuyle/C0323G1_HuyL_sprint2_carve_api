package com.example.carve.deck.controller;

import com.example.carve.deck.service.IDeckService;
import com.example.carve.deck.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deck")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DeckController {
    private final IDeckService deckService;
    private final ITagService tagService;

    @GetMapping("/get-tags")
    public ResponseEntity<List<String>> getTagsByDeckId(@RequestParam Long id) {
        var tags = tagService.findTagsByDeckId(id);
        if (tags == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDeck(@RequestBody CreateDeckRequest request) {
        if (deckService.createDeck(request)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
