package com.example.ufo_fi.v2.payment.persistence;

import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailLogRepository extends JpaRepository<FailLog, Long> {

}
