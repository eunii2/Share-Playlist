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

// 생성자 만들어주고

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto){
        Playlist playlist = new Playlist(playlistRequestDto);
        return playlistRepository.save(playlist);
    }

    public List<PlaylistRequestDto> getAllPlaylists() {
        return playlistRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private PlaylistRequestDto convertEntityToDto(Playlist playlist) {
        PlaylistRequestDto dto = new PlaylistRequestDto(playlist);
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