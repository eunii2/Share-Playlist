package com.msp.playlist.repository;

import com.msp.playlist.entity.Playlist;
import com.msp.playlist.entity.PlaylistMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    public Playlist findByPlaylistMembers(PlaylistMember playlistMember);
    //필요한 경우 추가 메소드 정의
}