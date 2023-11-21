package com.msp.playlist.controller;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Long> createPlaylist(@RequestBody @Valid PlaylistRequestDto playlistRequestDto){
        Long id = playlistService.createPlaylist(playlistRequestDto).getId();
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public List<PlaylistRequestDto> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @PutMapping("/{id}")
    public Playlist updatePlaylist(@PathVariable Long id, @RequestBody @Valid PlaylistUpdateDto updateDto) {
        return playlistService.updatePlaylist(id, updateDto);
        // TODO ~ 예외처리 후 코드 작성
        /*if (updatedPlaylist != null) {

        }*/
        // 적절한 예외 처리 필요
        //return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public String deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return "ok";
    }
}