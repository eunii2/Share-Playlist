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

    private String userid;

    public SimplePlaylistDto(Playlist playlist) {
        this.image = playlist.getImageUrl();
        this.playlistName = playlist.getName();
        this.userid = playlist.getMember().getUserid();
    }
}
