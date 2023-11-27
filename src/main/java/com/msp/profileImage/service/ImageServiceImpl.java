package com.msp.profileImage.service;

import com.msp.profileImage.entity.Image;
import com.msp.membership.entity.Member;
import com.msp.profileImage.dto.ImageResponseDTO;
import com.msp.profileImage.dto.ImageUploadDTO;
import com.msp.profileImage.repository.ImageRepository;
import com.msp.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;  // final 또는 @NonNull 인 필드들만을 인자로 받는 생성자를 자동으로 생성

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; //업로드 파일 다루는데 사용

import java.io.File;    // 파일시스템 관련 클래스
import java.io.IOException; // 입출력 예외 처리 클래스
import java.util.UUID;  // Universally Unique Identifier 생성 클래스

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Value("${file.profileImagePath}")  // application.properties 파일에서 file.profileImagePath 값을 가져옴
    private String uploadFolder;    // 업로드된 이미지를 저장할 디렉토리 경로를 저장하는 변수

    @Override
    public void upload(ImageUploadDTO imageUploadDTO, String userid) {  // 이미지와 사용자 ID를 받아 이미지를 업로드
        Member member = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        MultipartFile file = imageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();  // 파일 이름 곂치지 않도록 UUID 생성
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + imageFileName);  // 이미지 파일의 이름을 생성하고 저장할 경로 생성

        try {
            file.transferTo(destinationFile);   // 파일을 지정한 경로로 이동

            Image image = imageRepository.findByMember(member);
            if (image != null) {
                // 이미지가 이미 존재하면 url 업데이트
                image.updateUrl("/profileImages/" + imageFileName);
            } else {
                // 이미지가 없으면 객체 생성 후 저장
                image = Image.builder()
                        .member(member)
                        .url("/profileImages/" + imageFileName)
                        .build();
            }
            imageRepository.save(image);
        } catch (IOException e) { // 예외 발생
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageResponseDTO findImage(String userid) {
        Member member = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        Image image = imageRepository.findByMember(member);

        String defaultImageUrl = "/profileImages/cat.png";  // 디폴트 이미지를 이미 선언

        if (image == null) {
            return ImageResponseDTO.builder() // 이미지가 없으면 기본 이미지 반환
                    .url(defaultImageUrl)
                    .build();
        } else {
            return ImageResponseDTO.builder() // 이미지 있으면 해당 이미지 정보 반환
                    .url(image.getUrl())
                    .build();
        }
    }
}
