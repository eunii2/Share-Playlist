package com.msp.playlist.service;

import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.dto.SimplePlaylistDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.entity.PlaylistMember;
import com.msp.playlist.entity.TagGenre;
import com.msp.playlist.entity.TagMood;
import com.msp.playlist.repository.PlaylistMemberRepository;
import com.msp.playlist.repository.PlaylistRepository;
import com.msp.playlist.repository.TagGenreRepository;
import com.msp.playlist.repository.TagMoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TagGenreRepository tagGenreRepository;
    private final TagMoodRepository tagMoodRepository;
    private final PlaylistMemberRepository playlistMemberRepository;
    private final MemberRepository memberRepository;


    public PlaylistService(PlaylistRepository playlistRepository, TagGenreRepository tagGenreRepository,
                           TagMoodRepository tagMoodRepository, PlaylistMemberRepository playlistMemberRepository, MemberRepository memberRepository) {
        this.playlistRepository = playlistRepository;
        this.tagGenreRepository = tagGenreRepository;
        this.tagMoodRepository = tagMoodRepository;
        this.memberRepository = memberRepository;
        this.playlistMemberRepository = playlistMemberRepository;
    }

    public void grantAccess(Long playlistId, Long memberId, boolean canEdit) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new NoSuchElementException("Playlist not found with id: " + playlistId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));

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
        Playlist playlist = new Playlist(playlistRequestDto);
        //밑에서부터 tag 기능 추가
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
        return playlistRepository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    /* 선미 */
    public List<SimplePlaylistDto> getPlaylistsByUserId(String userId) {
        List<Playlist> playlists = playlistRepository.findByUserId(userId);
        List<SimplePlaylistDto> playlistDtos = playlists.stream()
                .map(SimplePlaylistDto::new)
                .collect(Collectors.toList());
        return playlistDtos;
    }



    private PlaylistRequestDto convertEntityToDto(Playlist playlist) {
        return new PlaylistRequestDto(playlist);
    }

    public Playlist updatePlaylist(Long id, PlaylistUpdateDto updateDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("this id not exists id: " + id));
        playlist.changeNameAndDescription(updateDto);
        // 예외 처리 또는 null 반환 등의 처리 필요
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