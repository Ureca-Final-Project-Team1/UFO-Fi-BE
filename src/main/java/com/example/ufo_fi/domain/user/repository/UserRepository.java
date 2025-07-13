package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
    SELECT u 
    FROM User u 
    JOIN FETCH u.userPlan
    WHERE u.id = :userId
    """)
    Optional<User> findUserWithUserPlan(@Param("userId") Long userId);

    List<User> findAllByIdIn(List<Long> followerIds);
}
