package com.msp.playlist.entity;

import com.msp.playlist.dto.PlaylistRequestDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

// GETTER SETTER 다 없애줘라 ~ LOMBOK
@Entity
@Getter //member 변수들이 전부 private 밖으로 가져오기 위해]
@Table(name="playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name_playlist")
    private String name;

    @Column(name="description_playlist")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "userid")
    private Long userId;

    public Playlist(PlaylistRequestDto playlistRequestDto){
        this.name = playlistRequestDto.getName();
        this.description = playlistRequestDto.getDescription();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = playlistRequestDto.getUserID();
    }

    public Playlist() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    //Getters and Setters
}
