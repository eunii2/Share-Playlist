package com.msp.song.repository;

import com.msp.song.dto.SongResponseDto;
import com.msp.song.entity.Song;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongMapper {
    public SongResponseDto mapToResponseDto(Song song){
        return new SongResponseDto(
                song.getTitle(),
                song.getYoutubeUrl(),
                song.getImageUrl(),
                song.getArtistName(),
                song.getId()
        );
    }
}
