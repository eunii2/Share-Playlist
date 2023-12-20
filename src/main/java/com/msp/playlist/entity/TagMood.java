package com.msp.playlist.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "mood")
public class TagMood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}