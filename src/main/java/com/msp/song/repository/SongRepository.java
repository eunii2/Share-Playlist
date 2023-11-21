package com.msp.song.repository;

import com.msp.playlist.entity.Playlist;
import com.msp.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    public List<Song> findByPlaylist(Playlist playlist);
}
