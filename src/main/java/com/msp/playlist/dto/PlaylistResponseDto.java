package com.msp.playlist.dto;


import java.util.List;

public record PlaylistResponseDto(
        String name,
        Long playlistId,
        String userName,
        String tagGenre,
        List<String> tagMood
) {

}
