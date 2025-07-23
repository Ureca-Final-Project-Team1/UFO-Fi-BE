package com.example.ufo_fi.domain.bannedword.repository;

import com.example.ufo_fi.domain.bannedword.entity.BannedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedWordRepository extends JpaRepository<BannedWord, Long> {

    boolean existsByWord(String word);
}
