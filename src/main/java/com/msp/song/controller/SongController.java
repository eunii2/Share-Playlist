package com.msp.song.controller;

import com.msp.song.dto.SongRequestDto;
import com.msp.song.entity.Song;
import com.msp.song.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Long> createSong(@RequestBody SongRequestDto dto) {
        Long songId = songService.createSong(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(songId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSong(@PathVariable Long id, @RequestBody SongRequestDto dto) {
        songService.updateSong(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getAllSong(@PathVariable Long id) {
        Song song = songService.getAllSong(id);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }
}
