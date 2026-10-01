package com.postservice.entity;

import com.postservice.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String postImageUrl;
    private String description;

    private Long userId;
    private String name;
    private String profileImage;
    private Long collegeId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PUBLIC;

    @Builder.Default
    private Long likeCount=0L;
    @Builder.Default
    private Long commentCount=0L;

    private String publicId;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
