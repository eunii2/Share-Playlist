package com.msp.playlist.dto;

import com.msp.playlist.entity.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistRequestDto {
    private String name;
    private String description;
    private Long userID;

    public PlaylistRequestDto(Playlist playlist){
        this.name = playlist.getName();
        this.description = playlist.getDescription();
        this.userID = playlist.getUserId();
    }
}