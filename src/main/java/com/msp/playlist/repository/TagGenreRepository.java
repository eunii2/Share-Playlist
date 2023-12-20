package com.msp.playlist.repository;

import com.msp.playlist.entity.TagGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagGenreRepository extends JpaRepository<TagGenre, Long> {
    Optional<TagGenre> findByName(String name);
}
