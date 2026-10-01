package com.postservice.dto;

import com.postservice.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String postImageUrl;
    private String description;
    private Visibility visibility;
    private String publicId;
}
