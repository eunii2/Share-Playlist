package com.msp.playlist.entity;

import com.msp.playlist.dto.PlaylistRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// GETTER SETTER 다 없애줘라 ~ LOMBOK
@Entity
@Getter //member 변수들이 전부 private 밖으로 가져오기 위해]
@Setter
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

    public Playlist(){
        this.name = PlaylistRequestDto.name;
    }

/*
    public void setUpdatedAt(LocalDateTime now) {
    }
*/

/*    public void setCreatedAt(LocalDateTime now) {
    }*/

    public void setUserId(Object userId) {
    }

/*
    public void setDescription(Object description) {
    }
*/

    public void setName(Object name) {
    }

    public Object getId() {
        return null;
    }

    public Object getName() {
        return null;
    }

    public Object getDescription() {
        return null;
    }

    public Object getCreatedAt() {
        return null;
    }

    public Object getUpdatedAt() {
        return null;
    }

    public Object getUserId() {
        return null;
    }

    public void setDescription(String description) {
    }

    public void setUpdatedAt(LocalDateTime now) {
    }

    //Getters and Setters
}
