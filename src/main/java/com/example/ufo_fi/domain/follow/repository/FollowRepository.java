package com.example.ufo_fi.domain.follow.repository;

import com.example.ufo_fi.domain.follow.entity.Follow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followingUser fu
    JOIN FETCH fu.profilePhoto
    WHERE f.followerUser.id = :userId
    """)
    List<Follow> findAllByFollowerUserId(Long userId);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followerUser fu
    JOIN FETCH fu.profilePhoto
    WHERE f.followingUser.id = :userId
    """)
    List<Follow> findAllByFollowingUserId(Long userId);
}
