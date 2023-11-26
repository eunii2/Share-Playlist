package com.msp.profileImage.service;

import com.msp.profileImage.dto.ImageResponseDTO;
import com.msp.profileImage.dto.ImageUploadDTO;

public interface ImageService {

    void upload(ImageUploadDTO imageUploadDTO, String userid);

    ImageResponseDTO findImage(String userid);
}