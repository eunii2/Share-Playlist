package com.msp.profileImage.controller;

import com.msp.profileImage.dto.ImageUploadDTO;
import com.msp.profileImage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ImageController {
    private final ImageService imageService;
    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDTO imageUploadDTO, @RequestParam String userId)
    {
        imageService.upload(imageUploadDTO, userId);
        return "/member/profile";  // 마이페이지(이미지 변경 페이지)
    }
}
