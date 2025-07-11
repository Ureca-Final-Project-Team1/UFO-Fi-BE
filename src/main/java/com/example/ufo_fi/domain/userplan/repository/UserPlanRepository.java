package com.example.ufo_fi.domain.userplan.repository;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    List<UserPlan> findAllByCarrier(Carrier carrier);
}
