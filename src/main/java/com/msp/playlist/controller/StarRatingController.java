package com.msp.playlist.controller;

import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.StarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/playlist")
public class StarRatingController {
    @PostMapping
    public ResponseEntity<Long> createStar(@RequestBody @Valid StarDto starDto){
        //TODO()
    }
}
