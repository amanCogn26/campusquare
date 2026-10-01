package com.collegeservice.repository;

import com.collegeservice.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {

    Optional<College> findByCollegeName(String collegeName);

    Optional<College>findByEmailDomain(String domain);
}
