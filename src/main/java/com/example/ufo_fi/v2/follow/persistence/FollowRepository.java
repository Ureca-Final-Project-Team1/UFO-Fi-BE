package com.example.ufo_fi.v2.follow.persistence;

import com.example.ufo_fi.v2.follow.domain.Follow;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(
        value = """
        SELECT f
        FROM Follow f
        JOIN FETCH f.followingUser fu
        JOIN FETCH fu.profilePhoto
        WHERE f.followerUser.id = :userId
        """,
        countQuery = """
        SELECT COUNT(f)
        FROM Follow f
        WHERE f.followerUser.id = :userId
        """
    )
    Page<Follow> findAllByFollowerUserId(Long userId, PageRequest pageRequest);

    @Query(
        value = """
        SELECT f
        FROM Follow f
        JOIN FETCH f.followingUser fu
        JOIN FETCH fu.profilePhoto
        WHERE f.followingUser.id = :userId
        """,
        countQuery = """
        SELECT COUNT(f)
        FROM Follow f
        WHERE f.followingUser.id = :userId
        """
    )
    Page<Follow> findAllByFollowingUserId(Long userId, PageRequest pageRequest);

    Long countByFollowingUser_Id(Long userId);

    Long countByFollowerUser_Id(Long userId);

    Optional<Follow> findByFollowingUserIdAndFollowerUserId(Long followingId, Long user);
}
