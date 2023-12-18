package com.msp.song.entity;

import com.msp.playlist.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


    public void setImageUrl(String s) {
        this.imageUrl = imageUrl;
    }

    public void setYoutubeUrl(String s) {
        this.youtubeUrl = youtubeUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String s) {
        this.artistName = artistName;
    }
}
