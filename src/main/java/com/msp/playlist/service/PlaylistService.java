package com.msp.playlist.service;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto){
        Playlist playlist = new Playlist();
        playlist.setName(playlistRequestDto.getName());
        playlist.setDescription(playlistRequestDto.getDescription());
        playlist.setUserId(playlistRequestDto.getUserId());
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUpdatedAt(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }

    public List<PlaylistRequestDto> getAllPlaylists() {
        return playlistRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private PlaylistRequestDto convertEntityToDto(Playlist playlist) {
        PlaylistRequestDto dto = new PlaylistRequestDto();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        dto.setCreatedAt(playlist.getCreatedAt());
        dto.setUpdatedAt(playlist.getUpdatedAt());
        dto.setUserId(playlist.getUserId());
        return dto;
    }

    public Playlist updatePlaylist(Long id, PlaylistUpdateDto updateDto) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            playlist.setName(updateDto.getName());
            playlist.setDescription(updateDto.getDescription());
            playlist.setUpdatedAt(LocalDateTime.now());
            return playlistRepository.save(playlist);
        }
        // 예외 처리 또는 null 반환 등의 처리 필요
        return null;
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}