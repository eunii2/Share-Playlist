package com.msp.song.controller;

import com.msp.song.dto.SongRequestDto;
import com.msp.song.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
