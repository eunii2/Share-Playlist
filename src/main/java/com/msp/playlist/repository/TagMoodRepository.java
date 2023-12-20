package com.msp.playlist.repository;

import com.msp.playlist.entity.Playlist;
import com.msp.playlist.entity.TagGenre;
import com.msp.playlist.entity.TagMood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagMoodRepository extends JpaRepository<TagMood, Long> {
    public List<TagMood> findByPlaylist(Playlist playlist);
    Optional<TagMood> findByName(String name);
}
