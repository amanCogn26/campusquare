package com.postservice.service.Implementation;

import com.postservice.dto.LoginResponse;
import com.postservice.enums.Visibility;
import com.postservice.exception.ForbiddenException;
import com.postservice.exception.ResourceNotFoundException;
import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.entity.Post;
import com.postservice.feignClient.InteractionClient;
import com.postservice.feignClient.UserClient;
import com.postservice.repository.PostRepository;
import com.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserClient userClient;
    private final InteractionClient interactionClient;

    public PostResponseDto postMethod(
            PostRequestDto postRequestDto,Long userId,Long collegeId){

        log.info("Post service is called");

        LoginResponse user = userClient.findUserById(userId);

        Post post=Post.builder()
                .postImageUrl(postRequestDto.getPostImageUrl())
                .description(postRequestDto.getDescription())
                .visibility(postRequestDto.getVisibility() != null
                                ? postRequestDto.getVisibility() : Visibility.PUBLIC)
                .userId(userId)
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .collegeId(collegeId)
                .publicId(postRequestDto.getPublicId())
                .build();

        Post post1=postRepository.save(post);

        log.info("Post is Created by userId {}",post.getUserId());

        return mapToDto(post1);
    }

    public void deletePost(Long postId,Long userId){

        log.info("Delete service is called for post id {}",postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.warn("Post does not exist {}", postId);

                    return new ResourceNotFoundException("Post does not exist");
                });

        if (!post.getUserId().equals(userId)) {

            log.warn("Unauthorized delete attempt");
            throw new ForbiddenException("You can delete only your own posts");
        }

        postRepository.delete(post);
//        interactionClient.deleteCommentsByPostId(postId);
//        interactionClient.deleteLikesByPostId(postId);

        log.info("Post deleted successfully {}", postId);
    }


    // It is for the getting the post into the feed
    public List<PostResponseDto>getAllPost(){

        log.info("Get All post Service is called");

        return postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // This is for getting the post for the user id
    public List<PostResponseDto>getAllPostByUserId(Long userId){

        log.info("Get all post by user id {}",userId);

        List<Post> posts = postRepository.findAllByUserId(userId);

        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found for userId " + userId);
        }

        log.info("Post is found , no error");

        return posts.stream()
                .map(post -> {

                    PostResponseDto dto =
                            mapToDto(post);

                    boolean liked =
                            interactionClient.isLikedByUser(
                                    post.getPostId(),
                                    userId
                            );
                    dto.setLikeByCurrentUser(liked);
                    return dto;
                })
                .toList();
    }

    public PostResponseDto getPostByPostId(Long postId,Long collegeId){

        log.info("Get post by post id {}",postId);

        Post post1=postRepository.findById(postId)
                .orElseThrow(() ->{
                        log.warn("Post does not exist by post id {}", postId);
                        return new ResourceNotFoundException("Post does not exist by post id: "+postId);
                });

        if(!collegeId.equals(post1.getCollegeId())){
            throw new ForbiddenException("Access denied");
        }

//        boolean liked =
//                interactionClient.isLikedByUser(
//                        post1.getPostId(),
//                        post1.getUserId()
//                );

        return mapToDto(post1);
    }

    public boolean exist(Long postId){
        log.info("exist service called");
        return postRepository.existsById(postId);
    }

    @Override
    public List<PostResponseDto> getFeed(Long collegeId,Long userId) {

        log.info("Get post by college id {}", collegeId);

        List<Post> posts = postRepository.findAllByCollegeIdOrderByCreatedAtDesc(collegeId);

        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found for college id " + collegeId);
        }

        posts = posts.stream()
                .filter(post -> !post.getUserId().equals(userId))
                .toList();

        return posts.stream()
                .map(post -> {

                    PostResponseDto dto =
                            mapToDto(post);

                    boolean liked =
                            interactionClient.isLikedByUser(
                                    post.getPostId(),
                                    userId
                            );
                    dto.setLikeByCurrentUser(liked);
                    return dto;
                })
                .toList();
    }

    //like increment service
    public void incrementLikeCount(Long postId){

        log.info("increment service method called for post id {}",postId);

        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post does not exist"));

        post.setLikeCount(post.getLikeCount()+1);

        log.info("Post like count increase {}",post.getLikeCount());

        postRepository.save(post);
    }

    public void decrementLikeCount(Long postId){

        log.info("decrement service method called for post id {}",postId);

        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post does not exist"));

        post.setLikeCount(Math.max(0L, post.getLikeCount() - 1));

        log.info("Post like count increase {}",post.getLikeCount());

        postRepository.save(post);
    }

    //comment service
    public void incrementCommentCount(Long postId){

        log.info("increment service method called for post id {}",postId);

        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post does not exist"));

        post.setCommentCount(post.getCommentCount()+1);

        log.info("Post like count increase {}",post.getCommentCount());

        postRepository.save(post);
    }

    public void decrementCommentCount(Long postId){

        log.info("decrement service method called for post id {}",postId);

        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post does not exist"));

        post.setCommentCount(Math.max(0L, post.getCommentCount() - 1));

        log.info("Post like count increase {}",post.getCommentCount());

        postRepository.save(post);
    }


    private PostResponseDto mapToDto(Post post) {

        return PostResponseDto.builder()
                .postImageUrl(post.getPostImageUrl())
                .postId(post.getPostId())
                .userId(post.getUserId())
                .description(post.getDescription())
                .visibility(post.getVisibility())
                .createdAt(post.getCreatedAt())
                .collegeId(post.getCollegeId())
                .publicId(post.getPublicId())
                .likeCount(post.getLikeCount())
                .name(post.getName())
                .profileImage(post.getProfileImage())
                .updatedAt(post.getUpdatedAt())
                .commentCount(post.getCommentCount())
                .build();
    }
}
