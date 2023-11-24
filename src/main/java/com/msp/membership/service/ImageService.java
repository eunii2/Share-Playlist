package com.msp.membership.service;

import com.msp.membership.dto.ImageResponseDTO;
import com.msp.membership.dto.ImageUploadDTO;

public interface ImageService {

    void upload(ImageUploadDTO imageUploadDTO, String userid);

    ImageResponseDTO findImage(String userid);
}