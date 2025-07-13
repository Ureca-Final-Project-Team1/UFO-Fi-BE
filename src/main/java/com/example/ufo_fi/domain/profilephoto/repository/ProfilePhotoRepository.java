package com.example.ufo_fi.domain.profilephoto.repository;

import com.example.ufo_fi.domain.profilephoto.entity.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
}
