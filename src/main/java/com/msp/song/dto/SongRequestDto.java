package com.msp.song.dto;

import com.msp.song.entity.Song;
import com.sun.istack.NotNull;

public record SongRequestDto(
        String title,
        String youtubeUrl,
        String imageUrl,
        String artistName,
        Long playlistId
) {

    public Song toSongEntity() {
        return Song.builder()
                .imageUrl(imageUrl)
                .youtubeUrl(youtubeUrl)
                .title(title)
                .artistName(artistName)
                .build();
    }
}
