package com.msp.playlist.dto;

import com.msp.song.dto.SongResponseDto;

import java.util.List;

public record PlaylistCheckDto(
        String name,
        Long playlistId,
        String userName,
        String description,
        String tagGenre,
        List<String> tagMood,
        List<SongResponseDto> songs
) {
}
