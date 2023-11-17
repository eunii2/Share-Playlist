package com.msp.playlist.service;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto){
        Playlist playlist = new Playlist();
        playlist.setName(playlistRequestDto.getName());
        playlist.setDescription(playlistRequestDto.getDescription());
        playlist.setUserId(playlistRequestDto.getUserId());
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUpdatedAt(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }
}
