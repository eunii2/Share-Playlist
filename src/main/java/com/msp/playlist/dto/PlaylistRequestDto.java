package com.msp.playlist.dto;

import com.msp.playlist.entity.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlaylistRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Min(value = 1, message = "최소 1 이상하셈 ㅋ")
    private String userid;

    private Long tagGenreId;
    private List<Long> tagMoodIds;
    public PlaylistRequestDto(Playlist playlist){
        this.name = playlist.getName();
        this.description = playlist.getDescription();
        this.userid = playlist.getMember().getUserid();
    }


    public Long getTagGenreId() {
        return tagGenreId;
    }

    public void setTagGenreId(Long tagGenreId) {
        this.tagGenreId = tagGenreId;
    }

    public List<Long> getTagMoodIds() {
        return tagMoodIds;
    }

    public void setTagMoodIds(List<Long> tagMoodIds) {
        this.tagMoodIds = tagMoodIds;
    }
}