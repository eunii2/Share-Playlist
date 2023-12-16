package com.msp.playlist.service;

import com.msp.membership.entity.Follow;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.dto.SimplePlaylistDto;
import com.msp.playlist.entity.*;
import com.msp.playlist.repository.PlaylistMemberRepository;
import com.msp.playlist.repository.PlaylistRepository;
import com.msp.playlist.repository.TagGenreRepository;
import com.msp.playlist.repository.TagMoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TagGenreRepository tagGenreRepository;
    private final TagMoodRepository tagMoodRepository;
    private final PlaylistMemberRepository playlistMemberRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private Member member;


    public PlaylistService(PlaylistRepository playlistRepository, TagGenreRepository tagGenreRepository,
                           TagMoodRepository tagMoodRepository, PlaylistMemberRepository playlistMemberRepository,
                           MemberRepository memberRepository, FollowRepository followRepository) {
        this.playlistRepository = playlistRepository;
        this.tagGenreRepository = tagGenreRepository;
        this.tagMoodRepository = tagMoodRepository;
        this.memberRepository = memberRepository;
        this.playlistMemberRepository = playlistMemberRepository;
        this.followRepository = followRepository; // 추가
    }


    public void grantAccess(Long playlistId, Long memberId, boolean canEdit) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        PlaylistMember playlistMember = new PlaylistMember();
        playlistMember.setPlaylist(playlist);
        playlistMember.setMember(member);
        playlistMember.setCanEdit(canEdit);
        playlistMemberRepository.save(playlistMember);
    }

    public boolean canEditPlaylist(Long playlistId, Long memberId) {
        return (boolean) playlistMemberRepository.findByPlaylistIdAndMemberId(playlistId, Math.toIntExact(memberId))
                .map(PlaylistMember::isCanEdit)
                .orElse(false);
    }

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto){
        Playlist playlist = new Playlist(playlistRequestDto, member);

        Member owner = memberRepository.findById(playlistRequestDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + playlistRequestDto.getOwnerId()));

        PlaylistMember playlistMember = new PlaylistMember();
        playlistMember.setPlaylist(playlist);
        playlistMember.setMember(owner);
        playlistMember.setGrade(Grade.OWNER);

        Set<PlaylistMember> members = new HashSet<>();
        members.add(playlistMember);

        playlist.setMembers(members);

        if(playlistRequestDto.getTagGenreId() != null){
            TagGenre tagGenre = tagGenreRepository.findById(playlistRequestDto.getTagGenreId()).orElseThrow();
            playlist.setTagGenre(tagGenre);
        }

        if(playlistRequestDto.getTagMoodIds() != null){
            for(Long moodId : playlistRequestDto.getTagMoodIds()) {
                TagMood tagMood = tagMoodRepository.findById(moodId).orElseThrow();
                tagMood.setPlaylsit(playlist);
                playlist.getTagMoods().add(tagMood);
            }
        }
        return playlistRepository.save(playlist);
    }

    public List<PlaylistRequestDto> getAllPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        Member member = memberRepository.findByUserid(userId);

        if(member == null){
            throw new EntityNotFoundException("Member not found with user id : " + userId);
        }

        List<PlaylistMember> playlistMembers = playlistMemberRepository.findByMember(member);
        List<PlaylistRequestDto> playlists = new ArrayList<>();

        for (PlaylistMember playlistMember : playlistMembers) {
            Playlist playlist = playlistMember.getPlaylist();
            playlists.add(this.convertEntityToDto(playlist));
        }
        return playlists;
    }

    /* 본인 플리 찾기 */
    public List<SimplePlaylistDto> getPlaylistsByUserid(String userid) {
        List<Playlist> playlists = playlistRepository.findByMemberUserid(userid);
        List<SimplePlaylistDto> playlistDtosNew = playlists.stream()
                .map(SimplePlaylistDto::new)
                .collect(Collectors.toList());
        return playlistDtosNew;
    }

    /* 친구 플리 찾기 */
    public List<SimplePlaylistDto> getFollowingPlaylists(String userid) {
        List<Follow> followings = followRepository.findByFromUserUserid(userid);
        List<SimplePlaylistDto> follwerplaylists = new ArrayList<>();

        for (Follow follow : followings) {
            List<Playlist> memberPlaylists = playlistRepository.findByMemberUserid(follow.getToUser().getUserid());
            for (Playlist playlist : memberPlaylists) {
                follwerplaylists.add(new SimplePlaylistDto(playlist));
            }
        }
        return follwerplaylists;
    }

    private PlaylistRequestDto convertEntityToDto(Playlist playlist) {
        return new PlaylistRequestDto(playlist);
    }

    public Playlist updatePlaylist(Long id, PlaylistUpdateDto updateDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("this id not exists id: " + id));
        playlist.changeNameAndDescription(updateDto);
        return playlist;
    }

    public void deletePlaylist(Long id) {
        log.info("start delete Playlist id: {}", id);
        try {
            playlistRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("this id is not exists! id: {} message: {}", id, e.getMessage());
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            log.error("delete Playlist failed id: {} message: {}", id, e.getMessage());
            throw new RuntimeException();
        }
    }
}