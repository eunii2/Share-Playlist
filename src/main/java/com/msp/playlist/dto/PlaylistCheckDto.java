package com.msp.playlist.dto;

import com.msp.song.dto.SongResponseDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record PlaylistCheckDto(
        String name,
        Long playlistId,
        String userName,
        String description,
        String tagGenre,
        List<String> tagMood,
        String imageUrl,
        List<SongResponseDto> songs

) {
}
