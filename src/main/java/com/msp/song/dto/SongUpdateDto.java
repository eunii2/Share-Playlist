package com.msp.song.dto;

public record SongUpdateDto(
        String title,
        String youtubeUrl,
        String imageUrl,
        String artistName
) {
}
