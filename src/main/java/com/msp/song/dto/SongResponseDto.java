package com.msp.song.dto;

public record SongResponseDto(
        String title,
        String youtubeUrl,
        String imageUrl,
        String artistName,
        Long songId
) {
}
