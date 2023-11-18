package com.msp.playlist.controller;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto){
        Playlist createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping
    public ResponseEntity<List<PlaylistRequestDto>> getAllPlaylists() {
        List<PlaylistRequestDto> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistUpdateDto updateDto) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(id, updateDto);
        if (updatedPlaylist != null) {
            return ResponseEntity.ok(updatedPlaylist);
        }
        // 적절한 예외 처리 필요
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok().build();
    }
}