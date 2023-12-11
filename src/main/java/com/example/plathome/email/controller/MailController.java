package com.example.plathome.email.controller;

import com.example.plathome.global.constant.EmailStaticField;
import com.example.plathome.email.service.MailMemberService;
import com.example.plathome.email.dto.request.MailForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/email")
@RequiredArgsConstructor
@RestController
public class MailController {
    private final MailMemberService mailMemberService;

    @PostMapping("/no-auth/send-email")
    public String sendMessage(@RequestBody @Valid MailForm mailForm) throws Exception {
        mailMemberService.sendCodeToEmail(mailForm);
        return EmailStaticField.SEND_MESSAGE;
    }


}
