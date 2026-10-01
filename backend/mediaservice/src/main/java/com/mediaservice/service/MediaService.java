package com.mediaservice.service;

import com.mediaservice.dto.MediaUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    //MultipartFile-->spring receives uploaded files
    // MultipartFile is an object (interface) provided by Spring.

    MediaUploadResponseDto uploadImage(MultipartFile file);
    void deleteImage(String publicId);
}
