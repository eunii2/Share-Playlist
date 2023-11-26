package com.msp.profileImage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile; //파일 업로드 관련 인터페이스

@Data
public class ImageUploadDTO {
    private MultipartFile file; //업로드된 파일 참조
}
