package com.example.plathome.email.provider;

import com.example.plathome.global.constant.EmailStaticField;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String email, String authCode) {
        SimpleMailMessage mailForm = createMailForm(email, authCode);
        mailSender.send(mailForm);
    }

    private SimpleMailMessage createMailForm(String email, String authCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(EmailStaticField.TITLE);
        simpleMailMessage.setText(authCode);
        return simpleMailMessage;
    }
}
