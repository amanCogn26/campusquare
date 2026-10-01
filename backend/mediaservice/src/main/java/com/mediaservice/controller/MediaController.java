package com.mediaservice.controller;

import com.mediaservice.dto.MediaUploadResponseDto;
import com.mediaservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    //upload image
    @PostMapping("/upload")
    public ResponseEntity<MediaUploadResponseDto>uploadImage(
            @RequestParam("file")MultipartFile file
            ){
        return ResponseEntity.ok(mediaService.uploadImage(file));
    }

    //delete image
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void>deleteImage(
            @PathVariable String publicId
    ){
        mediaService.deleteImage(publicId);

        return ResponseEntity.noContent().build();
    }
}
