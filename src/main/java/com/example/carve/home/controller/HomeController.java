package com.example.carve.home.controller;

import com.example.carve.home.dto.DeckDTO;
import com.example.carve.home.service.IHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class HomeController {
    private final IHomeService homeService;

    @GetMapping("/get-latest-decks")
    public ResponseEntity<List<DeckDTO>> findLatestDecksForHomePage(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "", required = false) String tag
    ) {
        List<DeckDTO> decks = homeService.findLatestDecksForHomePage(keyword, tag);
        if (decks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }

    @GetMapping("/get-detail")
    public ResponseEntity<DeckDTO> getDeckDetail(
            @RequestParam Long id
    ) {
        DeckDTO deck = homeService.getDeckDetail(id);
        if (deck == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deck, HttpStatus.OK);
        }
    }

    @GetMapping("/get-list-pagination")
    public ResponseEntity<Page<DeckDTO>> getListWithPagination(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit,
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "asc", required = false) String sortDirection,
            @RequestParam(defaultValue = "price", required = false) String sortBy
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<DeckDTO> decks = homeService.getListWithPagination(keyword, pageable);
        if (decks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }
}
