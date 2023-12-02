package com.example.plathome.email.service;


import com.example.plathome.email.exception.ExpiredAuthCodeException;
import com.example.plathome.email.exception.InvalidAuthCodeException;
import com.example.plathome.email.dto.request.MailForm;
import com.example.plathome.email.provider.MailService;
import com.example.plathome.email.service.redis.AuthCodeRedisService;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_DUPLICATE_EMAIL;


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
        String email = mailForm.email();
        this.validDupEmail(email);
        String authCode = this.createCode();
        mailService.sendEmail(email, authCode);
        authCodeRedisService.setData(email, authCode);
    }

    private void validDupEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            throw new DuplicationMemberException(ERROR_MEMBER_DUPLICATE_EMAIL);
        }
    }

    private String createCode() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        int authCode = random.nextInt(900000) + 100000;
        return String.valueOf(authCode);
    }

    public void verifyCode(String email, String authCode) {
        if (!authCodeRedisService.checkExistValue(email)) {
            throw new ExpiredAuthCodeException();
        } else if (!authCodeRedisService.getData(email).equals(authCode)) {
            throw new InvalidAuthCodeException();
        }
        authCodeRedisService.deleteData(email);
    }
}
