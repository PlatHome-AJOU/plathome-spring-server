package com.example.plathome.service;

import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비지니스 로직 - 회원")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;


}