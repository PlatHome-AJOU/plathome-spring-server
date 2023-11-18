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

    public void sendEmail(String userId, String authCode) {
        SimpleMailMessage mailForm = createMailForm(userId, authCode);
        mailSender.send(mailForm);
    }

    private SimpleMailMessage createMailForm(String userId, String authCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userId);
        simpleMailMessage.setSubject(TITLE);
        simpleMailMessage.setText(authCode);
        return simpleMailMessage;
    }
}
