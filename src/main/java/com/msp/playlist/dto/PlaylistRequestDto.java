package com.msp.playlist.dto;

import com.msp.playlist.entity.Playlist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistRequestDto {
    private String name;
    private String description;
    private Long userID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PlaylistRequestDto(Playlist playlist){
        this.name = playlist.getName();
        this.description = playlist.getDescription();
        this.userID = playlist.getUserId();
        this.createdAt = playlist.getCreatedAt();
        this.updatedAt = playlist.getUpdatedAt();
    }
}