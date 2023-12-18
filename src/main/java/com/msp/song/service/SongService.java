package com.msp.song.service;

import com.msp.playlist.entity.Playlist;
import com.msp.playlist.repository.PlaylistRepository;
import com.msp.song.dto.SongRequestDto;
import com.msp.song.entity.Song;
import com.msp.song.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SongService {
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;

    public SongService(
            SongRepository songRepository,
            PlaylistRepository playlistRepository
    ) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
    }

    @Transactional
    public Long createSong(SongRequestDto dto) {
        Playlist playlist = playlistRepository.findById(dto.playlistId())
                .orElseThrow(() -> new IllegalArgumentException("this id not have id: " + dto.playlistId()));

        Song songEntity = dto.toSongEntity();
        songEntity.setPlaylist(playlist);
        playlist.getSongs().add(songEntity);

        return songRepository.save(songEntity).getId();
    }

    @Transactional
    public Long updateSong(Long songId, SongRequestDto dto){
        Song song = songRepository.findById(songId)
                .orElseThrow(()-> new IllegalArgumentException("No song found with id: " + songId));

        song.setImageUrl(dto.imageUrl());
        song.setYoutubeUrl(dto.youtubeUrl());
        song.setTitle(dto.title());
        song.setArtistName(dto.artistName());

        return song.getId();
    }

    @Transactional(readOnly = true)
    public Song getAllSong(Long songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("No song found with id: " + songId));
    }

    @Transactional
    public void deleteSong(Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("No song found with id: " + songId));

        songRepository.delete(song);
    }
}
