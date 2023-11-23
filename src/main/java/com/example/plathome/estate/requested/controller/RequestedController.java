package com.example.plathome.estate.requested.controller;

import com.example.plathome.estate.requested.dto.request.RequestedForm;
import com.example.plathome.estate.requested.dto.response.RequestedResponse;
import com.example.plathome.estate.requested.service.RequestedService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/requested")
@RequiredArgsConstructor
@RestController
public class RequestedController {

    private final RequestedService requestedService;

    @PostMapping("/save/file")
    public String saveFile(
            @Login MemberSession memberSession,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        requestedService.saveFile(memberSession, multipartFile);
        return "success";
    }

    @PostMapping("/save/form")
    public String saveForm(
            @Login MemberSession memberSession,
            @RequestBody RequestedForm requestedForm
    ) {
        requestedService.saveForm(memberSession, requestedForm);
        return "success";
    }

    @GetMapping
    public List<RequestedResponse> getAll(
            @Admin MemberSession memberSession
    ) {
        return requestedService.getAll();
    }

    @PostMapping("/update/file")
    public String updateFile(
            @Login MemberSession memberSession,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        requestedService.updateFile(memberSession, multipartFile);
        return "success";
    }

    @PostMapping("/update/form")
    public String updateForm(
            @Login MemberSession memberSession,
            @RequestBody RequestedForm requestedForm
    ) {
        requestedService.updateForm(memberSession, requestedForm);
        return "success";
    }

    @PostMapping("/{userId}")
    public String delete(
            @Admin MemberSession memberSession,
            @PathVariable String userId
    ) {
        requestedService.delete(userId);
        return "success";
    }
}
