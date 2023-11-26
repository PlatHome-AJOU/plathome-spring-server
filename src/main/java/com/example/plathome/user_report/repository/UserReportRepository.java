package com.example.plathome.user_report.repository;

import com.example.plathome.user_report.domain.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
