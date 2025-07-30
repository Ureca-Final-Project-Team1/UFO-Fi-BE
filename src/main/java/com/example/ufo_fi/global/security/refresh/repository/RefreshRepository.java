package com.example.ufo_fi.global.security.refresh.repository;

import com.example.ufo_fi.v2.user.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
}
