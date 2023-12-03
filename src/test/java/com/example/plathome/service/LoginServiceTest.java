package com.example.plathome.service;

import com.example.plathome.ObjectBuilder;
import com.example.plathome.email.service.MailMemberService;
import com.example.plathome.login.dto.request.LoginForm;
import com.example.plathome.login.dto.request.SignUpForm;
import com.example.plathome.login.dto.response.MemberResponse;
import com.example.plathome.login.dto.response.TokenResponse;
import com.example.plathome.login.provider.JwtProvider;
import com.example.plathome.login.service.LoginService;
import com.example.plathome.login.service.redis.RefreshTokenRedisService;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.exception.NotMatchMemberPasswordException;
import com.example.plathome.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.plathome.login.constant.JwtStaticField.BEARER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@DisplayName("비지니스 로직 - 로그인")
@ExtendWith(MockitoExtension.class)
class LoginServiceTest extends ObjectBuilder {
    @InjectMocks private LoginService sut;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MemberRepository memberRepository;
    @Mock private JwtProvider jwtProvider;
    @Mock private RefreshTokenRedisService refreshTokenRedisService;
    @Mock private MailMemberService mailMemberService;

    @DisplayName("회원가입: input - SignUpForm | output - MemberResponse")
    @Test
    void givenSignUpForm_whenSingingUp_thenReturnsMemberResponse(){
        //given
        SignUpForm signUpForm = createSignUpForm();
        Member member = createMember();
        given(memberRepository.findByEmailOrNickname(signUpForm.email(), signUpForm.nickname()))
                .willReturn(Optional.empty());
        willDoNothing().given(mailMemberService).verifyCode(signUpForm.email(), signUpForm.authCode());
        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        MemberResponse memberResponse = sut.signUp(signUpForm);

        //then
        assertThat(memberResponse)
                .hasFieldOrPropertyWithValue("id", member.getId())
                .hasFieldOrPropertyWithValue("nickname", member.getNickname())
                .hasFieldOrPropertyWithValue("email", member.getEmail());
        then(memberRepository).should().findByEmailOrNickname(signUpForm.email(), signUpForm.nickname());
        then(mailMemberService).should().verifyCode(signUpForm.email(), signUpForm.authCode());
        then(memberRepository).should().save(any(Member.class));
    }

    @DisplayName("회원가입: input - SignUpForm With Dup Email | output - 400")
    @Test
    void givenSignUpFormWithDuplicationEmail_whenSingingUp_thenReturnsDuplicationException(){
        //given
        SignUpForm signUpForm = createSignUpForm();
        Member member = createMember();
        given(memberRepository.findByEmailOrNickname(signUpForm.email(), signUpForm.nickname()))
                .willReturn(Optional.of(createMember()));

        //when & then
        assertThrows(DuplicationMemberException.class, () -> sut.signUp(signUpForm));
        then(memberRepository).should().findByEmailOrNickname(signUpForm.email(), signUpForm.nickname());
        then(memberRepository).shouldHaveNoMoreInteractions();
        then(mailMemberService).shouldHaveNoInteractions();
    }

    @DisplayName("회원가입: input - SignUpForm With Dup Nickname | output - 400")
    @Test
    void givenSignUpFormWithDuplicationNickname_whenSingingUp_thenReturnsDuplicationExceptions(){
        //given
        SignUpForm wrongEmailSignUpForm = createWrongEmailSignUpForm();
        Member member = createMember();
        given(memberRepository.findByEmailOrNickname(wrongEmailSignUpForm.email(), wrongEmailSignUpForm.nickname()))
                .willReturn(Optional.of(createMember()));

        //when & then
        assertThrows(DuplicationMemberException.class, () -> sut.signUp(wrongEmailSignUpForm));
        then(memberRepository).should().findByEmailOrNickname(wrongEmailSignUpForm.email(), wrongEmailSignUpForm.nickname());
        then(memberRepository).shouldHaveNoMoreInteractions();
        then(mailMemberService).shouldHaveNoInteractions();
    }

    @DisplayName("로그인: input - LoginForm | output - TokensResponse")
    @Test
    void givenLoginForm_whenLogin_thenReturnsTokenResponse(){
        //given
        LoginForm loginForm = createLoginForm();
        Member member = createMember();
        String accessToken = createAccessToken(SECRET_KEY);
        String refreshToken = createRefreshToken(SECRET_KEY);
        given(memberRepository.findByEmail(loginForm.email())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(loginForm.password(), member.getPassword())).willReturn(true);
        given(jwtProvider.createAccessToken(member.getId().toString())).willReturn(accessToken);
        given(jwtProvider.createRefreshToken(member.getId().toString())).willReturn(refreshToken);
        willDoNothing().given(refreshTokenRedisService).setData(member.getId().toString(), refreshToken);

        //when
        TokenResponse tokenResponse = sut.login(loginForm);

        //then
        assertThat(tokenResponse)
                .hasFieldOrPropertyWithValue("accessToken", BEARER + accessToken)
                .hasFieldOrPropertyWithValue("refreshToken", BEARER + refreshToken);
        then(memberRepository).should().findByEmail(loginForm.email());
        then(passwordEncoder).should().matches(loginForm.password(), member.getPassword());
        then(jwtProvider).should().createAccessToken(member.getId().toString());
        then(jwtProvider).should().createRefreshToken(member.getId().toString());
        then(refreshTokenRedisService).should().setData(member.getId().toString(), refreshToken);
    }

    @DisplayName("로그인: input - LoginForm With Wrong Email | output - 404")
    @Test
    void givenLoginFormWithWrongEmail_whenLogin_thenReturnsNotFoundException(){
        //given
        LoginForm loginForm = createLoginForm();
        given(memberRepository.findByEmail(loginForm.email())).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.login(loginForm));
        then(memberRepository).should().findByEmail(loginForm.email());
        then(passwordEncoder).shouldHaveNoInteractions();
        then(jwtProvider).shouldHaveNoInteractions();
        then(refreshTokenRedisService).shouldHaveNoInteractions();
    }

    @DisplayName("로그인: input - LoginForm With Wrong Password | output - 405")
    @Test
    void givenLoginFormWithWrongPassword_whenLogin_thenReturnsNotMatchException(){
        //given
        LoginForm loginForm = createLoginForm();
        Member member = createMember();
        given(memberRepository.findByEmail(loginForm.email())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(loginForm.password(), member.getPassword())).willReturn(false);

        //when & then
        assertThrows(NotMatchMemberPasswordException.class, () -> sut.login(loginForm));
        then(memberRepository).should().findByEmail(loginForm.email());
        then(passwordEncoder).should().matches(loginForm.password(), member.getPassword());
        then(jwtProvider).shouldHaveNoInteractions();
        then(refreshTokenRedisService).shouldHaveNoInteractions();
    }

    @DisplayName("재발급: input - MemberSession | output - TokenResponse")
    @Test
    void givenMemberSession_whenRefreshing_thenReturnsTokenResponse(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        String stringMemberId = String.valueOf(memberSession.id());
        String accessToken = createAccessToken(SECRET_KEY);
        String refreshToken = createRefreshToken(SECRET_KEY);
        given(jwtProvider.createAccessToken(stringMemberId)).willReturn(accessToken);
        given(jwtProvider.createRefreshToken(stringMemberId)).willReturn(refreshToken);
        willDoNothing().given(refreshTokenRedisService).setData(stringMemberId, refreshToken);

        //when
        TokenResponse tokenResponse = sut.refresh(memberSession);

        //then
        assertThat(tokenResponse)
                .hasFieldOrPropertyWithValue("accessToken", BEARER + accessToken)
                .hasFieldOrPropertyWithValue("refreshToken", BEARER + refreshToken);
        then(jwtProvider).should().createAccessToken(stringMemberId);
        then(jwtProvider).should().createRefreshToken(stringMemberId);
        then(refreshTokenRedisService).should().setData(stringMemberId, refreshToken);
    }

    @DisplayName("로그아웃: input - MemberSession | output - void")
    @Test
    void givenMemberSession_whenLogout_thenReturnsSuccess() {
        //given
        MemberSession memberSession = createMemberSession(ID);
        willDoNothing().given(refreshTokenRedisService).deleteData(memberSession.email());

        //when
        sut.logout(memberSession);

        //then
        then(refreshTokenRedisService).should().deleteData(memberSession.email());
    }
}
