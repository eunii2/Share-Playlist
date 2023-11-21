package com.msp.song.entity;

import com.msp.playlist.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "youtube_url")
    private String youtubeUrl;
    @Column(name = "title")
    private String title;
    @Column(name = "artist_name")
    private String artistName;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
