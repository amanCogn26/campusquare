package com.userservice.entity;

import com.userservice.enums.Gender;
import com.userservice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    @Column(unique = true)
    private String collegeEmail;

    private String password;

    @Column(unique = true)
    private String personalEmail;

    private String phone;

    private String profileImage;

    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String studentId;

    private Long collegeId;

    private String collegeName;

    private Integer graduationYear;

    @CreationTimestamp
    private Date createdAt;
}
