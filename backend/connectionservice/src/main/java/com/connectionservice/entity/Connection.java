package com.connectionservice.entity;

import com.connectionservice.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;

    @CreationTimestamp
    private Date createdAt;

    private Date updatedAt;

    private Date connectedAt;
}
