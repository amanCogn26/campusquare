package com.connectionservice.repository;

import com.connectionservice.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection,Long> {

    List<Connection> findAllBySenderIdOrReceiverId(Long userId,Long receiverId);

    @Query("""
    SELECT c FROM Connection c WHERE
    (c.senderId = :senderId AND c.receiverId = :receiverId)
    OR
    (c.senderId = :receiverId AND c.receiverId = :senderId)
    """)
    Optional<Connection> findConnectionBetweenUsers(Long senderId, Long receiverId);

    @Query("""
SELECT COUNT(c) > 0
FROM Connection c
WHERE
(
    c.senderId = :senderId
    AND c.receiverId = :receiverId
)
OR
(
    c.senderId = :receiverId
    AND c.receiverId = :senderId
)
""")
    boolean areConnected(
            Long senderId,
            Long receiverId);
}
