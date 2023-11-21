package com.msp.playlist.repository;

import com.msp.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    //필요한 경우 추가 메소드 정의
}
