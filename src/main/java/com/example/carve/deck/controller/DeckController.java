package com.example.carve.deck.controller;

import com.example.carve.deck.service.IDeckService;
import com.example.carve.deck.service.ITagService;
import com.example.carve.home.dto.DeckDTO;
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

    @GetMapping("/get-deck")
    public ResponseEntity<DeckDTO> getDeck(@RequestParam Long id) {
        DeckDTO deck = deckService.getDeck(id);
        if (deck == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deck, HttpStatus.OK);
        }
    }

    @PutMapping("/edit-deck")
    public ResponseEntity<?> editDeck (@RequestBody CreateDeckRequest request, @RequestParam Long id) {
        if (deckService.editDeck(request, id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
