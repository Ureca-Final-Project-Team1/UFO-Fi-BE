package com.example.ufo_fi.v2.follow.infrastructure;

import com.example.ufo_fi.v2.follow.domain.Follow;
import java.util.List;
import java.util.Optional;
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

    Long countByFollowingUser_Id(Long userId);

    Long countByFollowerUser_Id(Long userId);

    Optional<Follow> findByFollowingUserIdAndFollowerUserId(Long followingId, Long user);
}
