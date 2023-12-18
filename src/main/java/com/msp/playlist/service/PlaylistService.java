package com.msp.playlist.service;

import com.msp.membership.entity.Follow;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import com.msp.playlist.dto.*;
import com.msp.playlist.entity.*;
import com.msp.playlist.repository.*;
import com.msp.song.dto.SongResponseDto;
import com.msp.song.repository.SongRepository;
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
    private final PlaylistMapper playlistMapper;
    private final PlaylistRepository playlistRepository;
    private final TagGenreRepository tagGenreRepository;
    private final TagMoodRepository tagMoodRepository;
    private final PlaylistMemberRepository playlistMemberRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final SongRepository songRepository;
    private Member member;

    public PlaylistService(PlaylistMapper playlistMapper, PlaylistRepository playlistRepository,
                           TagGenreRepository tagGenreRepository,
                           TagMoodRepository tagMoodRepository, PlaylistMemberRepository playlistMemberRepository,
                           MemberRepository memberRepository, FollowRepository followRepository, SongRepository songRepository) {
        this.playlistMapper = playlistMapper;
        this.playlistRepository = playlistRepository;
        this.tagGenreRepository = tagGenreRepository;
        this.tagMoodRepository = tagMoodRepository;
        this.memberRepository = memberRepository;
        this.playlistMemberRepository = playlistMemberRepository;
        this.followRepository = followRepository; // 추가
        this.songRepository = songRepository;
    }

/*    public void grantAccess(Long playlistId, Long memberId, boolean canEdit) {
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
    }*/

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Member member;
        try {
            member = memberRepository.findByUserid(userId);
        } catch (Exception e) {
            log.error(userId + "is not exists");
            throw new IllegalArgumentException("해당 id의 멤버가 존재하지 않습니다.");
        }

        Playlist playlist = new Playlist(playlistRequestDto, member);
        // TODO~ add mood
        if (playlistRequestDto.getTagMoodIds() != null) {
            log.info("start adding moods");
            for (Long moodId : playlistRequestDto.getTagMoodIds()) {
                TagMood tagMood = tagMoodRepository.findById(moodId)
                        .orElseThrow(() -> new RuntimeException("Mood not found with id: " + moodId));
                playlist.addMood(tagMood);
            }
            log.info("finished adding moods");
        }
        // Member owner = memberRepository.findById(playlistRequestDto.getOwnerId())
        // .orElseThrow(() -> new RuntimeException("Owner not found with id: " +
        // playlistRequestDto.getOwnerId()));

        PlaylistMember playlistMember = new PlaylistMember();
        playlistMember.setPlaylist(playlist);
        playlistMember.setMember(member);
        playlistMember.setGrade(Grade.OWNER);

        Set<PlaylistMember> members = new HashSet<>();
        members.add(playlistMember);

        playlist.setMembers(members);

        if (playlistRequestDto.getTagGenreId() != null) {
            log.info("set tag genre");
            TagGenre tagGenre = tagGenreRepository.findById(playlistRequestDto.getTagGenreId()).orElseThrow();
            playlist.setTagGenre(tagGenre);
            log.info("finish tag genre");
        }

        if (playlistRequestDto.getTagMoodIds() != null) {
            log.info("start mood start");
            for (Long moodId : playlistRequestDto.getTagMoodIds()) {
                TagMood tagMood = tagMoodRepository.findById(moodId).orElseThrow();
                // tagMood.setPlaylsit(playlist);
                playlist.getTagMoods().add(tagMood);
            }
            log.info("finish mood set");
        }
        return playlistRepository.save(playlist);
    }

    // 플리 하나 조회
    public PlaylistCheckDto getPlaylistDetails(Long playlistId){
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id : " + playlistId));

        String tagGenreName = playlist.getTagGenre() != null ? playlist.getTagGenre().getName() : null;
        List<String> tagMoodNames = playlist.getTagMoods().stream()
                .map(TagMood::getName)
                .toList();

        List<SongResponseDto> songResponseDtos = playlist.getSongs().stream()
                .map(song -> new SongResponseDto(song.getTitle(), song.getYoutubeUrl(), song.getImageUrl(),
                        song.getArtistName(), song.getId()))
                .collect(Collectors.toList());

        return new PlaylistCheckDto(
                playlist.getName(),
                playlist.getId(),
                playlist.getMember().getUsername(),
                playlist.getDescription(),
                playlist.getTagGenre().getName(),
                playlist.getTagMoods().stream().map(TagMood::getName).toList(),
                songResponseDtos
        );
    }

    public List<PlaylistResponseDto> getAllPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Member member = memberRepository.findByUserid(userId);

        if (member == null) {
            throw new EntityNotFoundException("Member not found with user id : " + userId);
        }

        List<Playlist> playlistEntities = playlistRepository.findByMemberUseridAndDeleted(userId, Deleted.FALSE);
        return playlistEntities.stream()
                .map(playlistMapper::mapToResponseDto).toList();
    }

    /* 본인 플리 찾기 */
    public List<SimplePlaylistDto> getPlaylistsByUserid(String userid) {
        List<Playlist> playlists = playlistRepository.findByMemberUseridAndDeleted(userid, Deleted.FALSE);
        List<SimplePlaylistDto> playlistDtos = playlists.stream()
                .map(SimplePlaylistDto::new)
                .collect(Collectors.toList());
        return playlistDtos;
    }

    /* 친구 플리 찾기 */
    public List<SimplePlaylistDto> getFollowingPlaylists(String userid) {
        List<Follow> followings = followRepository.findByFromUserUserid(userid);
        List<SimplePlaylistDto> follwerplaylists = new ArrayList<>();

        for (Follow follow : followings) {
            List<Playlist> memberPlaylists = playlistRepository
                    .findByMemberUseridAndDeleted(follow.getToUser().getUserid(), Deleted.FALSE);
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

        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        log.info("start delete Playlist id: {}", id);
        try {
            Playlist playlist = playlistRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 id의 Playlist가 존재하지 않습니다."));

            playlist.toDelete();
            playlistRepository.save(playlist);
        } catch (EmptyResultDataAccessException e) {
            log.error("this id is not exists! id: {} message: {}", id, e.getMessage());
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            log.error("delete Playlist failed id: {} message: {}", id, e.getMessage());
            throw new RuntimeException();
        }
    }
}