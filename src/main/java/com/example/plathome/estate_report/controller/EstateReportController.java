package com.example.plathome.estate_report.controller;

import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.estate_report.dto.response.EstateReportResponse;
import com.example.plathome.estate_report.service.EstateReportService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/estate-report")
@RequiredArgsConstructor
@RestController
public class EstateReportController {
    private final EstateReportService estateReportService;

    @PostMapping("/auth")
    public String report(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid EstateReportForm estateReportForm
    ) {
        estateReportService.report(memberSession, estateReportForm);
        return "success";
    }

    @GetMapping("/auth/{estateReportId}")
    public EstateReportResponse getOne(
            @Parameter(hidden = true) @Admin MemberSession memberSession,
            @PathVariable long estateReportId
    ) {
        return estateReportService.getById(estateReportId);
    }

    @GetMapping("/auth")
    public List<EstateReportResponse> getAll(
            @Parameter(hidden = true) @Admin MemberSession memberSession
    ) {
        return estateReportService.getAll();
    }

    @DeleteMapping("/auth/{estateReportId}")
    public String delete(
            @Parameter(hidden = true) @Admin MemberSession memberSession,
            @PathVariable long estateReportId
    ) {
        estateReportService.delete(estateReportId);
        return "success";
    }
}
