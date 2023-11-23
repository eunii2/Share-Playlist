package com.msp.playlist.dto;

import com.msp.playlist.entity.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlaylistRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Min(value = 1, message = "최소 1 이상하셈 ㅋ")
    private Long userID;

    public PlaylistRequestDto(Playlist playlist){
        this.name = playlist.getName();
        this.description = playlist.getDescription();
        this.userID = playlist.getUserId();
    }

    public Long getTagGenreId() {
        return null;
    }

    public Iterable<? extends Long> getTagMoodIds() {
        return null;
    }
}