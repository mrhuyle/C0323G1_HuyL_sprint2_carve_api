package com.example.carve.deck.controller;

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
    private final ITagService tagService;

    @GetMapping("/get-tags")
    public ResponseEntity<List<String>> getTagsByDeckId(@RequestParam Long id) {
        var tags = tagService.findTagsByDeckId(id);
        System.out.println("tags: ________" + tags);
        if (tags == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(tags);
    }
}
