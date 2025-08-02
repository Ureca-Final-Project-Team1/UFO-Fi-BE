package com.example.ufo_fi.v2.follow.persistence;

import com.example.ufo_fi.v2.follow.domain.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countByFollowingUser_Id(Long userId);

    Long countByFollowerUser_Id(Long userId);

    Optional<Follow> findByFollowingUserIdAndFollowerUserId(Long followingId, Long user);

    @Query(
        value = """
        SELECT f.id
        FROM Follow f
        WHERE f.followerUser.id = :userId
        """,
        countQuery = """
        SELECT COUNT(f)
        FROM Follow f
        WHERE f.followingUser.id = :userId
        """
    )
    Page<Long> findAllIdByFollowingUserId(@Param(value = "userId") Long userId, PageRequest pageRequest);

    @Query(
        value = """
        SELECT f.id
        FROM Follow f
        WHERE f.followingUser.id = :userId
        """,
        countQuery = """
        SELECT COUNT(f)
        FROM Follow f
        WHERE f.followingUser.id = :userId
        """
    )
    Page<Long> findAllIdByFollowerUserId(@Param(value = "userId") Long userId, PageRequest pageRequest);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followerUser fu
    JOIN FETCH fu.profilePhoto
    WHERE f.id IN :ids
    """)
    List<Follow> findFollowingWithUserAndPhotoByIn(@Param(value = "ids") List<Long> ids);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followingUser fu
    JOIN FETCH fu.profilePhoto
    WHERE f.id IN :ids
    """)
    List<Follow> findFollowerWithUserAndPhotoByIn(@Param(value = "ids") List<Long> ids);
}
