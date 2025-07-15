package com.example.ufo_fi.domain.report.repository;

import com.example.ufo_fi.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
