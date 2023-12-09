package com.msp.playlist.repository;

import com.msp.membership.entity.Member;
import com.msp.playlist.entity.PlaylistMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistMemberRepository extends JpaRepository<PlaylistMember, Long> {
    Optional<PlaylistMember> findByPlaylistIdAndMemberId(Long playlist_id, int member_id);

    List<PlaylistMember> findByMember(Member member);
}