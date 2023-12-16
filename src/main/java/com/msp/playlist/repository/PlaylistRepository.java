package com.msp.playlist.repository;

import com.msp.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByMemberUserid(String userid);

    @Query("SELECT p FROM Playlist p WHERE p.tagGenre.id IN :genreIds")
    List<Playlist> findByTagGenreIds(@Param("genreIds") List<Long> genreIds);

    @Query("SELECT p FROM Playlist p JOIN p.tagMoods m WHERE m.id IN :moodIds")
    List<Playlist> findByTagMoodIds(@Param("moodIds") List<Long> moodIds);
}