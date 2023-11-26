package com.example.plathome.estate_report.repository;

import com.example.plathome.estate_report.domain.EstateReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateReportRepository extends JpaRepository<EstateReport, Long> {
}
