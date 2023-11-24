package com.msp.membership.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile; //파일 업로드 관련 인터페이스

@Data // 클래스에 대한 getter, setter, equals, hashCode, toString 메소드를 자동으로 생성(setter가 문제면 바꿀 예정)
public class ImageUploadDTO {
    private MultipartFile file; //업로드된 파일 참조
}
