package com.msp.playlist.entity;

import javax.persistence.*;

@Entity
@Table(name = "mood")
public class TagMood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

public void setPlaylsit(Playlist playlist) {
    }

}