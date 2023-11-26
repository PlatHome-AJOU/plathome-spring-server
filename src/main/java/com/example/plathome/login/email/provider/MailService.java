package com.example.plathome.login.email.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.example.plathome.login.email.common.EmailStaticField.TITLE;


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
        simpleMailMessage.setSubject(TITLE);
        simpleMailMessage.setText(authCode);
        return simpleMailMessage;
    }
}
