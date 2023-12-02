package com.example.plathome.estate.requested.controller;

import com.example.plathome.estate.requested.dto.request.DateTimeForm;
import com.example.plathome.estate.requested.dto.request.RequestedForm;
import com.example.plathome.estate.requested.dto.response.RequestedResponse;
import com.example.plathome.estate.requested.service.RequestedService;
import com.example.plathome.estate.requested.service.ThumbNailService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/requested")
@RequiredArgsConstructor
@RestController
public class RequestedController {

    private final RequestedService requestedService;
    private final ThumbNailService thumbNailService;

    @PostMapping("/auth/contract")
    public String saveContract(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestPart MultipartFile file
    ) {
        requestedService.saveContract(memberSession, file);
        return "success";
    }

    @PostMapping("/auth/form")
    public String saveForm(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid RequestedForm requestedForm
    ) {
        requestedService.saveForm(memberSession, requestedForm);
        return "success";
    }

    @GetMapping("/auth/all")
    public List<RequestedResponse> getAll(
            @Parameter(hidden = true) @Admin MemberSession memberSession
    ) {
        return requestedService.getAll();
    }

    @PatchMapping("/auth/contract")
    public String updateContract(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestPart MultipartFile file
    ) {
        requestedService.updateContract(memberSession, file);
        return "success";
    }

    @PatchMapping("/auth/form")
    public String updateForm(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid RequestedForm requestedForm
    ) {
        requestedService.updateForm(memberSession, requestedForm);
        return "success";
    }

    @DeleteMapping("/auth/{memberId}")
    public String delete(
            @Parameter(hidden = true) @Admin MemberSession memberSession,
            @PathVariable long memberId
    ) {
        requestedService.delete(memberId);
        return "success";
    }

    @PostMapping("/auth/thumb-nail")
    public LocalDateTime saveThumbNail(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestPart MultipartFile file
    ) {
        return thumbNailService.saveThumbNail(memberSession, file);
    }

    @DeleteMapping("/auth/thumb-nail")
    public String deleteThumbNail(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid DateTimeForm dateTimeForm
    ) {
        thumbNailService.deleteThumbNail(memberSession, dateTimeForm.fileName());
        return "success";
    }

    @GetMapping("/auth/{requestedId}")
    public RequestedResponse getOne(
            @Parameter(hidden = true) @Admin MemberSession memberSession,
            @PathVariable Long requestedId
    ) {
        return requestedService.getOne(requestedId);
    }
}
