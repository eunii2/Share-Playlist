package com.msp.profileImage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ImageResponseDTO {
    private String url;  // 이미지 url


    public ImageResponseDTO(String url){
        this.url = url;
    }
}
