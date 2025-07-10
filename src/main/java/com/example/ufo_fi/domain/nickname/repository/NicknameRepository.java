package com.example.ufo_fi.domain.nickname.repository;

import com.example.ufo_fi.domain.nickname.entity.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {
}
