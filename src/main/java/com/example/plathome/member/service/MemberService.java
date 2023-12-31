package com.example.plathome.member.service;


import com.example.plathome.login.dto.response.MemberResponse;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getBySession(MemberSession session) {
        return memberRepository.findByEmail(session.email())
                .map(MemberResponse::from)
                .orElseThrow(NotFoundMemberException::new);
    }

    public MemberResponse getUserById(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }

    public MemberSession getMemberSessionById(long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberSession::from)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional
    public void delete(MemberSession memberSession) {
        memberRepository.deleteById(memberSession.id());
    }
}
