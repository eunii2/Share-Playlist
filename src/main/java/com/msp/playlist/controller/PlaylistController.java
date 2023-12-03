package com.msp.playlist.controller;

import com.msp.playlist.dto.GrantAccessRequestDto;
import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{playlistId}/access/{member_tableId}")
    public boolean checkAccess(@PathVariable Long playlistId, @PathVariable Long member_tableId) {
        return playlistService.checkUserAccessToPlaylist(member_tableId, playlistId);
    }

    @PostMapping("/{playlistId}/grant-access")
    public ResponseEntity<?> grantAccess(@PathVariable Long playlistId, @RequestBody GrantAccessRequestDto request) {
        playlistService.grantAccess(playlistId, request.getMemberId(), request.isCanEdit());
        return ResponseEntity.ok().build();
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
    }

    @DeleteMapping("/{id}")
    public String deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return "ok";
    }
}