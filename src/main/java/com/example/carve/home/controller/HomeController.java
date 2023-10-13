package com.example.carve.home.controller;

import com.example.carve.home.dto.DeckForHomePageDTO;
import com.example.carve.home.service.IHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class HomeController {
    private final IHomeService homeService;

    @GetMapping("/get-latest-decks")
    public ResponseEntity<List<DeckForHomePageDTO>> findLatestDecksForHomePage(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "", required = false) String tag
    ) {
        List<DeckForHomePageDTO> decks = homeService.findLatestDecksForHomePage(keyword, tag);
        if (decks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }
}
