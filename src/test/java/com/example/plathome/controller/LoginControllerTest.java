package com.example.plathome.controller;

import com.example.plathome.controller.helper.LoginControllerTestHelper;
import com.example.plathome.login.member.domain.RefreshToken;
import com.example.plathome.login.member.dto.request.LoginForm;
import com.example.plathome.login.member.dto.request.SignUpForm;
import com.example.plathome.login.member.repository.RefreshTokenRepository;
import com.example.plathome.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.example.plathome.login.member.common.JwtStaticField.BEARER;
import static com.example.plathome.login.member.domain.SecretKey.KEY_PATH;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends LoginControllerTestHelper {
    private final MockMvc mvc;
    private final ObjectMapper mapper;
    private final Environment environment;
    LoginControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper,
            @Autowired Environment environment
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
        this.environment = environment;
    }

    @DisplayName("회원가입 성공 - 200")
    @Test
    void givenLoginForm_whenSigningUp_thenReturns200() throws Exception {
        //given
        SignUpForm signUpForm = createSignUpForm();
        given(jwtLoginService.signUp(signUpForm)).willReturn(createMemberWithoutTokenDto());

        //when
        mvc.perform(post("/jwt/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect((jsonPath("$.userId", is(USERID))));

        //then
        then(jwtLoginService).should().signUp(signUpForm);
    }

    @DisplayName("회원가입 실패 - 400 - UserId Not Email")
    @Test
    void givenWrongLoginForm_whenSigningUp_thenReturns400() throws Exception {
        //given
        SignUpForm wrongUserIdSignUpForm = createWrongUserIdSignUpForm();

        //when
        mvc.perform(post("/jwt/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrongUserIdSignUpForm)))
                .andExpect(status().isBadRequest());
        //then
        then(jwtLoginService).shouldHaveNoInteractions();
    }

    @DisplayName("로그인 성공 - 200")
    @Test
    void givenLoginForm_whenLogin_thenReturns200() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        LoginForm loginForm = createLoginForm();
        String refreshToken = createRefreshToken(secretKey);
        given(jwtLoginService.login(any(), any(), any())).willReturn(createMemberWithTokenDto(refreshToken));

        //when
        mvc.perform(post("/jwt/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect((jsonPath("$.userId", is(USERID))))
                .andExpect((jsonPath("$.refreshToken", is(refreshToken))));

        //then
        then(jwtLoginService).should().login(any(), any(), any());
    }

    @DisplayName("로그인 실패 - 400 - Blank Password")
    @Test
    void givenDupLoginForm_whenLogin_thenReturns400() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        LoginForm wrongPasswordLoginForm = createWrongPasswordLoginForm();
        String refreshToken = createRefreshToken(secretKey);

        //when
        mvc.perform(post("/jwt/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wrongPasswordLoginForm)))
                .andExpect(status().isBadRequest());

        //then
        then(jwtLoginService).shouldHaveNoInteractions();
    }

    @DisplayName("개인 회원 조회 - 200")
    @Test
    void givenAccessToken_whenGettingMemberInfo_thenReturns200() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        String accessToken = createAccessToken(secretKey);
        this.memberRepositorySetting();
        given(memberService.getBySession(any())).willReturn(createMemberWithoutTokenDto());

        //when
        mvc.perform(get("/jwt/get")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect((jsonPath("$.userId", is(USERID))));

        //then
        then(memberService).should().getBySession(any());
    }

    @DisplayName("개인 회원 조회 - 401 - AccessToken Expired")
    @Test
    void givenExpiredAccessToken_whenGettingMemberInfo_thenReturns401() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        String expiredAccessToken = createExpiredAccessToken(secretKey);

        //when
        mvc.perform(get("/jwt/get")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + expiredAccessToken))
                .andExpect(status().isUnauthorized());

        //then
        then(memberService).shouldHaveNoInteractions();
    }

    @DisplayName("개인 회원 조회 - 401 - AccessToken Invalid")
    @Test
    void givenInvalidAccessToken_whenGettingMemberInfo_thenReturns401() throws Exception {
        //given

        //when
        mvc.perform(get("/jwt/get")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ""))
                .andExpect(status().isUnauthorized());

        //then
        then(memberService).shouldHaveNoInteractions();
    }

    @DisplayName("엑세스 토큰 재발급 성공 - 200")
    @Test
    void givenRefreshToken_whenRefreshingAccessToken_thenReturns200() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        String refreshToken = createRefreshToken(secretKey);
        memberRepositorySetting();
        refreshTokenRepositorySetting(refreshToken);
        willDoNothing().given(jwtLoginService).refresh(any(),any());

        //when
        mvc.perform(post("/jwt/token")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + refreshToken))
                .andExpect(status().isOk());

        //then
        then(jwtLoginService).should().refresh(any(),any());
    }

    @DisplayName("엑세스 토큰 재발급 실패 - 401 - RefreshToken Expired")
    @Test
    void givenExpiredRefreshToken_whenRefreshingAccessToken_thenReturns401() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        String expiredRefreshToken = createExpiredRefreshToken(secretKey);

        //when
        mvc.perform(post("/jwt/token")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + expiredRefreshToken))
                .andExpect(status().isUnauthorized());

        //then
        then(jwtLoginService).shouldHaveNoInteractions();
    }

    @DisplayName("엑세스 토큰 재발급 실패 - 401 - RefreshToken Invalid")
    @Test
    void givenInvalidRefreshToken_whenRefreshingAccessToken_thenReturns401() throws Exception {
        //given

        //when
        mvc.perform(post("/jwt/token")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ""))
                .andExpect(status().isUnauthorized());

        //then
        then(jwtLoginService).shouldHaveNoInteractions();
    }

    @DisplayName("로그아웃 - 200")
    @Test
    void givenAccessToken_whenLogout_thenReturns200() throws Exception {
        //given
        String secretKey = environment.getProperty(KEY_PATH);
        String accessToken = createAccessToken(secretKey);
        memberRepositorySetting();
        willDoNothing().given(jwtLoginService).logout(any());

        //when
        mvc.perform(get("/jwt/logout")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andExpect(status().isOk());

        //then
        then(jwtLoginService).should().logout(any());
    }

    private void memberRepositorySetting() {
        given(memberRepository.findByUserId(any())).willReturn(Optional.of(createMember()));
    }

    private void refreshTokenRepositorySetting(String refreshToken) {
        given(refreshTokenRepository.getValidRefreshTokens()).willReturn(Map.of(USERID, Set.of(RefreshToken.of(refreshToken))));
    }
}