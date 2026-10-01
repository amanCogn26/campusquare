package com.chatservice.repository;

import com.chatservice.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    @Query("""
    select c from ChatMessage c where (senderId=:userId and receiverId=:receiverId) 
        or 
       (senderId=:receiverId and receiverId=:userId )
    """)

    List<ChatMessage>getConversation(Long userId, Long receiverId);
}
