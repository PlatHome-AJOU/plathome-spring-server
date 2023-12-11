package com.example.plathome.service;

import com.example.plathome.ObjectBuilder;
import com.example.plathome.login.dto.response.MemberResponse;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.domain.types.RoleType;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@DisplayName("비지니스 로직 - 회원")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest extends ObjectBuilder {

    @InjectMocks
    private MemberService sut;
    @Mock
    private MemberRepository memberRepository;
    
    @DisplayName("본인 조회: 올바른 MemberSession으로 본인을 조회한 경우 - 200")
    @Test
    void givenMemberSession_whenRequestingMemberInfo_thenReturnsMemberResponse(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.ofNullable(createMember()));

        //when
        MemberResponse memberResponse = sut.getBySession(memberSession);

        //then
        assertThat(memberResponse)
                .hasFieldOrPropertyWithValue("id", ID_1)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL);
        then(memberRepository).should().findByEmail(EMAIL);
    }

    @DisplayName("본인 조회: 존재하지 않는 Email로 MemberSession이 들어온 경우 - 404")
    @Test
    void givenNonExistentEmail_whenRequestingMemberInfo_thenReturnsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getBySession(memberSession));
        then(memberRepository).should().findByEmail(EMAIL);
    }

    @DisplayName("타인 조회: MemberId로 회원을 조회한 경우 - 200")
    @Test
    void givenMemberId_whenRequestingMemberInfo_thenReturnsMemberResponse(){
        //given
        long memberId = ID_1;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(createMember()));

        //when
        MemberResponse memberResponse = sut.getUserById(memberId);

        //then
        assertThat(memberResponse)
                .hasFieldOrPropertyWithValue("id", ID_1)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL);
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("타인 조회: 존재하지 않는 MemberId로 회원을 조회한 경우 - 404")
    @Test
    void givenNonExistentMemberId_whenRequestingMemberInfo_thenReturnsNotFoundException(){
        //given
        long memberId = ID_1;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getUserById(memberId));
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("인터 셉터: MemberId로 MemberSession을 조회하는 경우 - 200")
    @Test
    void givenMemberId_whenRequestingMemberSession_thenReturnsMemberSession(){
        //given
        long memberId = ID_1;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(createMember()));

        //when
        MemberSession memberSession = sut.getMemberSessionById(memberId);

        //then
        assertThat(memberSession)
                .hasFieldOrPropertyWithValue("id", ID_1)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL)
                .hasFieldOrPropertyWithValue("password", PASSWORD)
                .hasFieldOrPropertyWithValue("roleType", RoleType.USER);
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("인터 셉터: 존재하지 않는 MemberId로 MemberSession을 조회하는 경우 - 404")
    @Test
    void givenNonExistentMemberId_whenRequestingMemberSession_thenReturnsNotFoundException(){
        //given
        long memberId = ID_1;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getMemberSessionById(memberId));
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("회원 삭제: 회원 탈퇴를 진행할 경우 - 200")
    @Test
    void givenMemberSession_whenDeletingMember_thenSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        willDoNothing().given(memberRepository).deleteById(memberSession.id());

        //when
        sut.delete(memberSession);

        then(memberRepository).should().deleteById(memberSession.id());
    }

}