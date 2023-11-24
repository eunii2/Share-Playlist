package com.msp.membership.controller;

import com.msp.membership.dto.ImageUploadDTO;
import com.msp.membership.service.ImageService;
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
