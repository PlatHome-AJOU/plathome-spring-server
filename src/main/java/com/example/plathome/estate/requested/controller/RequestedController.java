package com.example.plathome.estate.requested.controller;

import com.example.plathome.estate.requested.dto.request.RequestedForm;
import com.example.plathome.estate.requested.dto.response.RequestedResponse;
import com.example.plathome.estate.requested.service.RequestedService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/requested")
@RequiredArgsConstructor
@RestController
public class RequestedController {

    private final RequestedService requestedService;

    @PostMapping("/auth/file")
    public String saveFile(
            @Login MemberSession memberSession,
            @RequestPart MultipartFile file
    ) {
        requestedService.saveFile(memberSession, file);
        return "success";
    }

    @PostMapping("/auth/form")
    public String saveForm(
            @Login MemberSession memberSession,
            @RequestBody @Valid RequestedForm requestedForm
    ) {
        requestedService.saveForm(memberSession, requestedForm);
        return "success";
    }

    @GetMapping("/auth")
    public List<RequestedResponse> getAll(
            @Admin MemberSession memberSession
    ) {
        return requestedService.getAll();
    }

    @PatchMapping("/auth/file")
    public String updateFile(
            @Login MemberSession memberSession,
            @RequestPart MultipartFile file
    ) {
        requestedService.updateFile(memberSession, file);
        return "success";
    }

    @PatchMapping("/auth/form")
    public String updateForm(
            @Login MemberSession memberSession,
            @RequestBody @Valid RequestedForm requestedForm
    ) {
        requestedService.updateForm(memberSession, requestedForm);
        return "success";
    }

    @DeleteMapping("/auth/{memberId}")
    public String delete(
            @Admin MemberSession memberSession,
            @PathVariable long memberId
    ) {
        requestedService.delete(memberId);
        return "success";
    }


}
