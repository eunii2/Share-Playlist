package com.msp.playlist.repository;

import com.msp.playlist.dto.PlaylistResponseDto;
import com.msp.playlist.entity.Playlist;
import com.msp.playlist.entity.TagMood;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistMapper {
    public PlaylistResponseDto mapToResponseDto(Playlist playlist){
        List<String> moods = playlist.getTagMoods().stream().map(TagMood::getName).toList();
        return new PlaylistResponseDto(
                playlist.getName(),
                playlist.getId(),
                playlist.getMember().getUsername(),
                playlist.getTagGenre().getName(),
                moods,
                playlist.getImageUrl()
        );
    }
}
