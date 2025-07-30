package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
}
