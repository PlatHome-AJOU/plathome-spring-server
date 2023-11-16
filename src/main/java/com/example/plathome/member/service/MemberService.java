package com.example.plathome.member.service;


import com.example.plathome.login.member.domain.UserContext;
import com.example.plathome.login.member.dto.MemberWithTokenDto;
import com.example.plathome.login.member.dto.request.SignUpForm;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberWithTokenDto getBySession(MemberSession session) {
        return memberRepository.findByUserId(session.userId())
                .map(MemberWithTokenDto::withoutToken)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional
    public void delete(MemberSession memberSession) {
        memberRepository.deleteById(memberSession.id());
    }
}
