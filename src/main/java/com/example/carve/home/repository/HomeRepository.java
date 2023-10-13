package com.example.carve.home.repository;

import com.example.carve.deck.model.Deck;
import com.example.carve.home.dto.DeckForHomePageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HomeRepository extends JpaRepository<Deck, Long> {
    @Query(value = " SELECT d.id as id, d.name as name, d.description as description,\n" +
            "            d.price as price, d.promo_percent as promoPercent, d.img as img,\n" +
            "            t.name as tagName\n" +
            "            FROM deck d\n" +
            "            JOIN tag t\n" +
            "            WHERE d.is_deleted = false AND d.is_product = true " +
            "           AND d.name LIKE :keyword " +
            "           AND t.name LIKE :tag " +
            "            ORDER BY d.created_time DESC ", nativeQuery = true)
    List<DeckForHomePageDTO> findLatestDecksForHomePage(@Param("keyword") String keyword, @Param("tag") String tag);
}
