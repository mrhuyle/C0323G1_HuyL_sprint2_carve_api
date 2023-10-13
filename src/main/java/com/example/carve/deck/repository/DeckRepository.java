package com.example.carve.deck.repository;

import com.example.carve.deck.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck,Long> {

}
