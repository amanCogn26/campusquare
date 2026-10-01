package com.postservice.dto;

import com.postservice.enums.Visibility;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PostResponseDto {

    private Long postId;
    private String postImageUrl;
    private String description;
    private Long collegeId;
    private Visibility visibility;
    private Date createdAt;
    private Date updatedAt;
    private Long likeCount;
    private Long commentCount;
    private String publicId;

    private String name;
    private Long userId;
    private String profileImage;

    private Boolean likeByCurrentUser;
}
