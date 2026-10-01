package com.mediaservice.service.Implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mediaservice.dto.MediaUploadResponseDto;
import com.mediaservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final Cloudinary cloudinary;

    @Override
    public MediaUploadResponseDto uploadImage(MultipartFile file){

        try{
            Map<?, ?>result=cloudinary.uploader()
                    .upload(
                            file.getBytes(),
                            ObjectUtils.emptyMap()
                    );

            return MediaUploadResponseDto.builder()
                    .imageUrl(result.get("secure_url").toString())
                    .publicId(result.get("public_id").toString())
                    .build();

        }catch (IOException e){
            log.error("Image upload failed");
            throw new RuntimeException("Image upload failed");
        }
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap());

        } catch (IOException e) {
            throw new RuntimeException("Image deletion failed");
        }
    }
}
