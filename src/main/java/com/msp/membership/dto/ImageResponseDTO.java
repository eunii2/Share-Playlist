package com.msp.membership.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponseDTO {
    private String url;  // 이미지 url

    @Builder
    public ImageResponseDTO(String url){
        this.url = url;
    }
}
