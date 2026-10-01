package com.postservice.repository;

import com.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    boolean existsById(Long postId);

    void deleteById(Long postId);

    List<Post> findAllByUserId(Long userId);

    Optional<Post>findById(Long postId);

    List<Post> findAllByCollegeIdOrderByCreatedAtDesc(Long collegeId);

}
