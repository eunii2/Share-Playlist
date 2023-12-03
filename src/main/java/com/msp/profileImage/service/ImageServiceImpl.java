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

    @Value("${file.profileImagePath}")
    private String uploadFolder;

    @Override
    public void upload(ImageUploadDTO imageUploadDTO, String userid) {
        Member member = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        MultipartFile file = imageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageResponseDTO findImage(String userid) {
        Member member = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        Image image = imageRepository.findByMember(member);

        String defaultImageUrl = "/profileImages/cat.png";

        if (image == null) {
            return ImageResponseDTO.builder()
                    .url(defaultImageUrl)
                    .build();
        } else {
            return ImageResponseDTO.builder()
                    .url(image.getUrl())
                    .build();
        }
    }
}
