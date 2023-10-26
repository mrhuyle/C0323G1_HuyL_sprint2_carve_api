package com.example.carve.deck.repository;

import com.example.carve.deck.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t.name FROM deck_tag dt JOIN tag t ON dt.tag_id = t.id WHERE dt.deck_id = :id", nativeQuery = true)
    List<String> findTagsByDeckId(@Param("id") Long id);
}
