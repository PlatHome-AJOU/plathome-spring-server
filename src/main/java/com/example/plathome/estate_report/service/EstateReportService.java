package com.example.plathome.estate_report.service;

import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.exception.NotFoundEstateException;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.estate_report.dto.response.EstateReportResponse;
import com.example.plathome.estate_report.exception.NotFoundEstateReportException;
import com.example.plathome.estate_report.repository.EstateReportRepository;
import com.example.plathome.member.domain.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EstateReportService {
    private final EstateReportRepository estateReportRepository;
    private final EstateRepository estateRepository;

    @Transactional
    public void report(MemberSession memberSession, EstateReportForm estateReportForm) {
        Estate estate = estateRepository.findById(estateReportForm.estateId())
                .orElseThrow(NotFoundEstateException::new);
        estateReportRepository.save(estateReportForm.toEntity(memberSession.id()));
    }

    public EstateReportResponse getById(long estateReportId) {
        return estateReportRepository.findById(estateReportId)
                .map(EstateReportResponse::from)
                .orElseThrow(NotFoundEstateReportException::new);
    }

    public List<EstateReportResponse> getAll() {
        return estateReportRepository.findAll().stream()
                .map(EstateReportResponse::from)
                .toList();
    }

    @Transactional
    public void delete(long estateReportId) {
        estateReportRepository.deleteById(estateReportId);
    }
}
