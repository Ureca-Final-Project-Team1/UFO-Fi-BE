package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {
}
