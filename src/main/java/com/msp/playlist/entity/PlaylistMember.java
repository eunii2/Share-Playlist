package com.msp.playlist.entity;

import com.msp.membership.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "playlist_member")
@NoArgsConstructor
public class PlaylistMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "can_edit")
    private boolean canEdit;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}
