package com.msp.playlist.repository;

import com.msp.playlist.entity.PlaylistMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistMemberRepository extends JpaRepository<PlaylistMember, Long> {
    boolean existsByMemberIdAndPlaylistId(Long memberId, Long playlistId);
}