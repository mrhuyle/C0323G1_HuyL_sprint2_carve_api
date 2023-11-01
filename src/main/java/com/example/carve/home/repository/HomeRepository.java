package com.example.carve.home.repository;

import com.example.carve.deck.model.Deck;
import com.example.carve.home.dto.DeckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeRepository extends JpaRepository<Deck, Long> {
    @Query(value = " SELECT d.id as id, d.name as name, d.description as description,\n" +
            "            d.price as price, d.promo_percent as promoPercent, d.img as img,\n" +
            "            JSON_ARRAYAGG(t.name) AS tagName, " +
            "            d.created_time as createdTime " +
            "           FROM deck d " +
            "           LEFT JOIN deck_tag dt ON d.id = dt.deck_id " +
            "           LEFT JOIN tag t ON dt.tag_id = t.id " +
            "           WHERE d.is_deleted = false AND d.is_product = true " +
            "           AND d.name LIKE :keyword " +
            "           AND t.name LIKE :tag " +
            "           GROUP BY d.id " +
            "           ORDER BY d.created_time DESC ", nativeQuery = true)
    List<DeckDTO> findLatestDecksForHomePage(@Param("keyword") String keyword, @Param("tag") String tag);

    @Query(value = " SELECT d.id as id, d.name as name, d.description as description,\n" +
            "            d.price as price, d.promo_percent as promoPercent, d.img as img,\n" +
            "            JSON_ARRAYAGG(t.name) AS tagName, " +
            "            d.created_time as createdTime " +
            "           FROM deck d " +
            "           LEFT JOIN deck_tag dt ON d.id = dt.deck_id " +
            "           LEFT JOIN tag t ON dt.tag_id = t.id " +
            "            WHERE d.is_deleted = false AND d.is_product = true " +
            "           AND d.id = :id ", nativeQuery = true)
    DeckDTO getDeckDetail(@Param("id") Long id);

    @Query(value = " SELECT d.id as id, d.name as name, d.description as description,\n" +
            "            d.price as price, d.promo_percent as promoPercent, d.img as img,\n" +
            "            JSON_ARRAYAGG(t.name) AS tagName, " +
            "            d.created_time as createdTime " +
            "           FROM deck d " +
            "           LEFT JOIN deck_tag dt ON d.id = dt.deck_id " +
            "           LEFT JOIN tag t ON dt.tag_id = t.id " +
            "           WHERE d.is_deleted = false AND d.is_product = true " +
            "           AND d.name LIKE :keyword " +
            "           OR t.name LIKE :keyword " +
            "           GROUP BY d.id ", nativeQuery = true)
    Page<DeckDTO> getListWithPagination(@Param("keyword") String keyword, Pageable pageable);
}
