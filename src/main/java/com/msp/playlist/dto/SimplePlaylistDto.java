package com.msp.playlist.dto;

import com.msp.playlist.entity.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimplePlaylistDto {
    private String playlistName;
    private String image;

    private String userId;

    public SimplePlaylistDto(Playlist playlist) {
        // this.image = playlist.getImage(); // Playlist Entity에 image 됐을 때
        this.playlistName = playlist.getName();
        this.userId = playlist.getUserId();
    }
}
