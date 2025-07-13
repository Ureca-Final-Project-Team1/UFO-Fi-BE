package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.InterestedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestedPostRepository extends JpaRepository<InterestedPost, Long> {
    Optional<InterestedPost> findByUserId(Long userId);
}
