package com.msp.playlist.entity;

import javax.persistence.*;

@Entity
@Table(name = "playlist_member")
public class PlaylistMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "member_table_id")
    private Long memberId;

    @Column(name = "can_edit")
    private boolean canEdit;

    public void setPlaylistId(Long playlistId) {
    }

    public void setMemberId(Long memberId) {
    }

    public void setCanEdit(boolean canEdit) {
    }
}
