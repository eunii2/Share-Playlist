package com.msp.playlist.controller;

import com.msp.playlist.dto.SimplePlaylistDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.dto.GrantAccessRequestDto;
import com.msp.playlist.dto.PlaylistRequestDto;
import com.msp.playlist.dto.PlaylistUpdateDto;
import com.msp.playlist.repository.PlaylistRepository;
import com.msp.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistRepository playlistRepository;
    private PlaylistController followService;
    private java.util.stream.Collectors Collectors;

    public PlaylistController(PlaylistService playlistService, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;
    }


    @PostMapping("/{playlistId}/grant-access")
    public ResponseEntity<?> grantAccess(@PathVariable Long playlistId, @RequestBody GrantAccessRequestDto request) {
        playlistService.grantAccess(playlistId, request.getMemberId(), request.isCanEdit());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{playlistId}/can-edit/{memberId}")
    public ResponseEntity<Boolean> canEdit(@PathVariable Long playlistId, @PathVariable Long memberId) {
        boolean canEdit = playlistService.canEditPlaylist(playlistId, memberId);
        return ResponseEntity.ok(canEdit);
    }

    @PostMapping
    public ResponseEntity<Long> createPlaylist(@RequestBody @Valid PlaylistRequestDto playlistRequestDto){
        Long id = playlistService.createPlaylist(playlistRequestDto).getId();
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public List<PlaylistRequestDto> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @PutMapping("/{id}")
    public Playlist updatePlaylist(@PathVariable Long id, @RequestBody @Valid PlaylistUpdateDto updateDto) {
        return playlistService.updatePlaylist(id, updateDto);
        // TODO ~ 예외처리 후 코드 작성
    }

    @DeleteMapping("/{id}")
    public String deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return "ok";
    }

    /* 메인 본인 플리 출력 */
    @GetMapping("/simple_my_playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SimplePlaylistDto>> getMyPlaylists(Principal principal) {
        String userid = principal.getName();
        List<SimplePlaylistDto> playlists = playlistService.getPlaylistsByUserid(userid);
        return ResponseEntity.ok(playlists);
    }

    /* 현재 사용자의 친구 플리 */
    @GetMapping("/simple_followings_playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SimplePlaylistDto>> getFollowingPlaylists(@RequestParam String userid) {

        List<SimplePlaylistDto> playlists = (List<SimplePlaylistDto>) playlistService.getFollowingPlaylists(userid);
        return ResponseEntity.ok(playlists);
    }

    /* 메인 태그 검색(선택) 기능 */
    @GetMapping("/playlistsTag")
    public List<Playlist> getPlaylistsByGenreAndMood(
            @RequestParam(required = false) List<Long> genreIds,
            @RequestParam(required = false) List<Long> moodIds) {
        if (genreIds != null && moodIds != null) {
            List<Playlist> playlistsByGenre = playlistRepository.findByTagGenreIds(genreIds);
            List<Playlist> playlistsByMood = playlistRepository.findByTagMoodIds(moodIds);
            playlistsByGenre.retainAll(playlistsByMood);
            return playlistsByGenre;
        } else if (genreIds != null) {
            return playlistRepository.findByTagGenreIds(genreIds);
        } else if (moodIds != null) {
            return playlistRepository.findByTagMoodIds(moodIds);
        } else {
            return new ArrayList<>();
        }
    }
}