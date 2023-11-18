package com.example.plathome.login.email.service;


import com.example.plathome.login.email.dto.request.MailForm;
import com.example.plathome.login.email.exception.ExpiredAuthCodeException;
import com.example.plathome.login.email.exception.InvalidAuthCodeException;
import com.example.plathome.login.email.provider.MailService;
import com.example.plathome.login.email.service.redis.AuthCodeRedisService;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MailMemberService {

    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final AuthCodeRedisService authCodeRedisService;

    @Transactional
    public void sendCodeToEmail(MailForm mailForm) throws Exception {
        String userId = mailForm.userId();
        this.validDupUserId(userId);
        String authCode = this.createCode();
        mailService.sendEmail(userId, authCode);
        authCodeRedisService.setData(userId, authCode);
    }

    private void validDupUserId(String userId) {
        Optional<Member> optionalUser = memberRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            throw new DuplicationMemberException();
        }
    }

    private String createCode() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        int authCode = random.nextInt(900000) + 100000;
        return String.valueOf(authCode);
    }

    public void verifyCode(String userId, String authCode) {
        if (!authCodeRedisService.checkExistValue(userId)) {
            throw new ExpiredAuthCodeException();
        } else if (!authCodeRedisService.getData(userId).equals(authCode)) {
            throw new InvalidAuthCodeException();
        }
        authCodeRedisService.deleteData(userId);
    }
}
