package com.msp.playlist.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PlaylistUpdateDto {

    @NotBlank
    private String name;

    @NotBlank(message = "빈 설명값을 넣으면 안됩니다.")
    private String description;

    @NotBlank
    private String imageUrl;


/*    public Object getName() {
        return null;
    }

    public Object getDescription() {
        return null;
    }*/

    // Getters and Setters
}