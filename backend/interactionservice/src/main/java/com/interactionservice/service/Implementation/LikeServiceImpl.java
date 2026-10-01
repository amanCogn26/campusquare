package com.interactionservice.service.Implementation;

import com.interactionservice.dto.LikeInteractionEvent;
import com.interactionservice.entity.Like;
import com.interactionservice.exception.ResourceNotFoundException;
import com.interactionservice.feignClient.PostClient;
import com.interactionservice.repository.LikeRepository;
import com.interactionservice.service.LikeService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostClient postClient;
    private final SimpMessagingTemplate messagingTemplate;

    public void likePost(Long postId,Long userId){

        log.info("log from like post service");

        if(!postClient.postExist(postId)){
            throw new ResourceNotFoundException("Post does not exist");
        }

        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            disLikePost(postId,userId);
            return;
        }

        Like like=Like.builder()
                    .postId(postId)
                    .userId(userId)
                    .build();

        likeRepository.save(like);

        postClient.incrementLikeCount(postId);

        Long likeCount = likeRepository.countByPostId(postId);

        LikeInteractionEvent event = LikeInteractionEvent.builder()
                                        .postId(postId)
                                        .userId(userId)
                                        .action("LIKE")
                                        .likeCount(likeCount)
                                        .build();

        log.info("Publishing event: {}", event);

        messagingTemplate.convertAndSend(
                "/topic/post/like",
                event
        );
    }
    public void disLikePost(Long postId,Long userId){

        log.info("log from like post service");

        if (!postClient.postExist(postId)) {
            throw new ResourceNotFoundException("Post does not exist");
        }

        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException("Like does not exist"));

        likeRepository.delete(like);

        postClient.decrementLikeCount(postId);

        Long likeCount = likeRepository.countByPostId(postId);

        LikeInteractionEvent event = LikeInteractionEvent.builder()
                .postId(postId)
                .userId(userId)
                .action("DISLIKE")
                .likeCount(likeCount)
                .build();

        log.info("Publishing event: {}", event);

        messagingTemplate.convertAndSend(
                "/topic/post/like",
                event
        );
    }

    public Boolean isLikedByUser(Long postId,Long userId) {

        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }
}
