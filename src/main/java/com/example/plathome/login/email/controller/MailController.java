package com.example.plathome.login.email.controller;

import com.example.plathome.login.email.dto.request.MailForm;
import com.example.plathome.login.email.service.MailMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.plathome.login.email.common.EmailStaticField.SEND_MESSAGE;

@RequestMapping("/api/email")
@RequiredArgsConstructor
@RestController
public class MailController {
    private final MailMemberService mailMemberService;

    @PostMapping("/send-email")
    public String sendMessage(@RequestBody @Valid MailForm mailForm) throws Exception {
        mailMemberService.sendCodeToEmail(mailForm);
        return SEND_MESSAGE;
    }


}
