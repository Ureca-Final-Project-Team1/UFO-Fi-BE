package com.example.ufo_fi.domain.userplan.repository;

import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
