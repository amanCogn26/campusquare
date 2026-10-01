package com.interactionservice.service.Implementation;

import com.interactionservice.dto.CommentInteractionEvent;
import com.interactionservice.dto.CommentRequestDto;
import com.interactionservice.dto.CommentResponseDto;
import com.interactionservice.dto.LoginResponse;
import com.interactionservice.entity.Comment;
import com.interactionservice.feignClient.PostClient;
import com.interactionservice.feignClient.UserClient;
import com.interactionservice.repository.CommentRepository;
import com.interactionservice.service.CommentService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostClient postClient;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @Transactional
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto,Long userId){

        log.info("Add comment request received for postId {} by userId {}",
                commentRequestDto.getPostId(), userId);

        if(!postClient.postExist(commentRequestDto.getPostId())){
            log.warn("Post is not exist with post id {}",commentRequestDto.getPostId());
            throw new NotFoundException("Post does not exist");
        }

        LoginResponse user = userClient.findUserById(userId);

        log.info("name is {}",user.getName());

        Comment comment=Comment.builder()
                .commentText(commentRequestDto.getCommentText())
                .postId(commentRequestDto.getPostId())
                .userId(userId)
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .build();

        commentRepository.save(comment);

        log.info("Comment created successfully for postId {} by userId {} by {}",
                commentRequestDto.getPostId(), userId,comment.getName());

        postClient.incrementCommentCount(commentRequestDto.getPostId());

        Long commentCount=commentRepository.countByPostId(commentRequestDto.getPostId());

        log.info("comment count is {}",commentCount);

         CommentResponseDto commentResponseDto= CommentResponseDto.builder()
                .postId(comment.getPostId())
                .commentId(comment.getCommentId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .commentText(comment.getCommentText())
                 .name(comment.getName())
                 .profileImage(comment.getProfileImage())
                .build();

        CommentInteractionEvent event = CommentInteractionEvent.builder()
                .commentResponseDto(commentResponseDto)
                .action("COMMENT_CREATED")
                .commentCount(commentCount)
                .build();

        simpMessagingTemplate.convertAndSend(
                "/topic/post/comment",
                event
        );

        return commentResponseDto;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId,Long userId){

        log.info("Delete comment request received for commentId {} by userId {}",
                commentId, userId);

        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new NotFoundException("Comment not found"));

        if(!comment.getUserId().equals(userId)){
            throw new ForbiddenException("You can delete only your own comments");
        }

        commentRepository.delete(comment);

        log.info("Comment deleted successfully for comment Id {} by userId {}",
                commentId, userId);

        postClient.decrementCommentCount(comment.getPostId());

        Long commentCount = commentRepository.countByPostId(comment.getPostId());

        CommentResponseDto commentResponseDto= CommentResponseDto.builder()
                .postId(comment.getPostId())
                .commentId(comment.getCommentId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .commentText(comment.getCommentText())
                .name(comment.getName())
                .profileImage(comment.getProfileImage())
                .build();

        CommentInteractionEvent event = CommentInteractionEvent.builder()
                .commentResponseDto(commentResponseDto)
                .action("COMMENT_DELETED")
                .commentCount(commentCount)
                .build();

        simpMessagingTemplate.convertAndSend(
                "/topic/post/comment",
                event
        );
    }

    @Override
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {

        log.info("Fetching comments for postId {}", postId);

        if(!postClient.postExist(postId)){
            throw new NotFoundException("Post does not exist");
        }

        log.info("get comments by post id is exist with the post id");

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        log.info("Comments found: {}", comments.size());

        return comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .postId(comment.getPostId())
                        .createdAt(comment.getCreatedAt())
                        .commentText(comment.getCommentText())
                        .name(comment.getName())
                        .profileImage(comment.getProfileImage())
                        .userId(comment.getUserId())
                        .build())
                .toList();
    }
}
