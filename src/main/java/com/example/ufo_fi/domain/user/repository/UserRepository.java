package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoId(String kakaoId);
}
