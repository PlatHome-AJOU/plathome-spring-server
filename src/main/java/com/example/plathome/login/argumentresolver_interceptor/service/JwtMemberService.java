package com.example.plathome.login.argumentresolver_interceptor.service;

import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtMemberService {
    private final MemberRepository memberRepository;

    public MemberSession getMemberSessionByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .map(MemberSession::from)
                .orElseThrow(NotFoundMemberException::new);
    }
}
