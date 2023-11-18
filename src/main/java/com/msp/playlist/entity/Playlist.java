package com.msp.playlist.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    public void setUpdatedAt(LocalDateTime now) {
    }

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setUserId(Object userId) {
    }

    public void setDescription(Object description) {
    }

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

    //Getters and Setters
}
