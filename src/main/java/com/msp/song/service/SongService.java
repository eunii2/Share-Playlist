package com.msp.song.service;

import com.msp.playlist.entity.Playlist;
import com.msp.playlist.repository.PlaylistRepository;
import com.msp.song.dto.SongRequestDto;
import com.msp.song.dto.SongResponseDto;
import com.msp.song.dto.SongUpdateDto;
import com.msp.song.entity.Song;
import com.msp.song.repository.SongMapper;
import com.msp.song.repository.SongRepository;
import com.msp.utils.HtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SongService {
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final SongMapper songMapper;
    private final HtmlParser htmlParser;

    public SongService(
            SongRepository songRepository,
            PlaylistRepository playlistRepository,
            SongMapper songMapper, HtmlParser htmlParser) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.songMapper = songMapper;
        this.htmlParser = htmlParser;
    }

    @Transactional
    public Long createSong(SongRequestDto dto) {
        Playlist playlist = playlistRepository.findById(dto.playlistId())
                .orElseThrow(() -> new IllegalArgumentException("this id not have id: " + dto.playlistId()));

        Song songEntity = dto.toSongEntity();
        songEntity.setPlaylist(playlist);
        playlist.getSongs().add(songEntity);
        String imageUrl = htmlParser.getOgImageUrlFromUrl(dto.youtubeUrl());
        songEntity.setImageUrl(imageUrl);

        return songRepository.save(songEntity).getId();
    }

    @Transactional
    public Long updateSong(Long songId, SongUpdateDto dto){
        Song song = songRepository.findById(songId)
                .orElseThrow(()-> new IllegalArgumentException("No song found with id: " + songId));

        song.setYoutubeUrl(dto.youtubeUrl());
        song.setTitle(dto.title());
        song.setArtistName(dto.artistName());

        return song.getId();
    }

    @Transactional(readOnly = true)
    public List<SongResponseDto> getAllSong(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id 값의 플리는 없음 : " + playlistId));
        return songRepository.findByPlaylist(playlist)
                .stream()
                .map(songMapper::mapToResponseDto)
                .toList();
    }

    @Transactional
    public void deleteSong(Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("No song found with id: " + songId));

        songRepository.delete(song);
    }
}
