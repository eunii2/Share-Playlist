package com.msp.playlist.entity;

import com.msp.membership.entity.Member;
import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.song.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name="playlist")
@NoArgsConstructor
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

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private Member member;

    @OneToMany(mappedBy = "playlist")
    private List<Song> songs = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "genreid")
    private TagGenre tagGenre;

    @OneToMany(mappedBy = "playlist")
    private List<TagMood> tagMoods = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Deleted deleted;
//    @OneToOne
//    private TagMood tagMoods;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private Set<PlaylistMember> members;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistMember> playlistMembers;

    public Long getUserId() {
        return member != null ? member.getId() : null;
    }

    public Playlist(PlaylistRequestDto playlistRequestDto, Member member){
        this.name = playlistRequestDto.getName();
        this.description = playlistRequestDto.getDescription();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.member = member;
        this.deleted = Deleted.FALSE;
        this.imageUrl = playlistRequestDto.getImageUrl();
    }

    public void changeNameAndDescription(PlaylistUpdateDto updateDto) {
        if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()) {
            this.name = updateDto.getName();
        }
        if (updateDto.getDescription() != null && !updateDto.getDescription().trim().isEmpty()) {
            this.description = updateDto.getDescription();
        }
        if (updateDto.getImageUrl() != null && !updateDto.getImageUrl().trim().isEmpty()) {
            this.imageUrl = updateDto.getImageUrl();
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void setTagGenre(TagGenre tagGenre) {
        this.tagGenre = tagGenre;
    }

    public void setPlaylistMembers(List<PlaylistMember> playlistMembers) {
        this.playlistMembers = playlistMembers;
    }

    public void setMembers(Set<PlaylistMember> members) {
        this.members = members;
    }

    public void addMood(TagMood mood){
        mood.setPlaylist(this);
        this.tagMoods.add(mood);
    }

    public void toDelete() {
        this.deleted = Deleted.TRUE;
    }

    public Member getUser(){
        return this.member;
    }
}
