package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT u
    FROM User u
    JOIN FETCH u.userPlan
    JOIN FETCH u.userAccount
    WHERE u.id = :userId
    """)
    Optional<User> findUserWithUserPlanAndUserAccountWithPessimisticLock(@Param("userId") Long userId);

    @Query("""
    SELECT u
    FROM User u
    JOIN FETCH u.userPlan up
    JOIN FETCH up.plan
    WHERE u.id = :userId
    """)
    Optional<User> findUserWithUserPlanAndPlan(Long userId);

    User findByKakaoId(String string);
}
