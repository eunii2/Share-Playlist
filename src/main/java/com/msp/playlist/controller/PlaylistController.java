package com.msp.playlist.controller;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto){
        Playlist createdPlaylist = playlistService.createPlaylist(playlistRequestDto);
        return ResponseEntity.ok(createdPlaylist);
    }
}
