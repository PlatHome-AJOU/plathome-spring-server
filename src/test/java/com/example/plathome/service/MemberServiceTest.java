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
    
    @DisplayName("본인조회: input - MemberSession | output - MemberResponse")
    @Test
    void givenMemberSession_whenRequestingMemberInfo_thenReturnsMemberResponse(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.ofNullable(createMember()));

        //when
        MemberResponse memberResponse = sut.getBySession(memberSession);

        //then
        assertThat(memberResponse)
                .hasFieldOrPropertyWithValue("id", ID)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL);
        then(memberRepository).should().findByEmail(EMAIL);
    }

    @DisplayName("본인조회: input - Wrong MemberSession | output - 404")
    @Test
    void givenWrongMemberSession_whenRequestingMemberInfo_thenReturnsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getBySession(memberSession));
        then(memberRepository).should().findByEmail(EMAIL);
    }

    @DisplayName("타인조회: input - MemberId | output - MemberResponse")
    @Test
    void givenMemberId_whenRequestingMemberInfo_thenReturnsMemberResponse(){
        //given
        long memberId = ID;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(createMember()));

        //when
        MemberResponse memberResponse = sut.getUserById(memberId);

        //then
        assertThat(memberResponse)
                .hasFieldOrPropertyWithValue("id", ID)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL);
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("타인조회: input - Wrong MemberId | output - 404")
    @Test
    void givenWrongMemberId_whenRequestingMemberInfo_thenReturnsNotFoundException(){
        //given
        long memberId = ID;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getUserById(memberId));
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("인터셉터: input - MemberId | output - MemberSession")
    @Test
    void givenMemberId_whenRequestingMemberSession_thenReturnsMemberSession(){
        //given
        long memberId = ID;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(createMember()));

        //when
        MemberSession memberSession = sut.getMemberSessionById(memberId);

        //then
        assertThat(memberSession)
                .hasFieldOrPropertyWithValue("id", ID)
                .hasFieldOrPropertyWithValue("nickname", NICKNAME)
                .hasFieldOrPropertyWithValue("email", EMAIL)
                .hasFieldOrPropertyWithValue("password", PASSWORD)
                .hasFieldOrPropertyWithValue("roleType", RoleType.USER);
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("인터셉터: input - Wrong MemberId | output - 404")
    @Test
    void givenWrongMemberId_whenRequestingMemberSession_thenReturnsNotFoundException(){
        //given
        long memberId = ID;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.getMemberSessionById(memberId));
        then(memberRepository).should().findById(memberId);
    }

    @DisplayName("회원삭제: input - MemberId | output - Void")
    @Test
    void givenMemberSession_whenDeletingMember_thenSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        willDoNothing().given(memberRepository).deleteById(memberSession.id());

        //when
        sut.delete(memberSession);

        then(memberRepository).should().deleteById(memberSession.id());
    }

}