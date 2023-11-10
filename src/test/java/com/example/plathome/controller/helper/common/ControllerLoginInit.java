package com.example.plathome.controller.helper.common;

import com.example.plathome.global.config.SecurityConfig;
import com.example.plathome.login.argumentresolver_interceptor.controller.LoginController;
import com.example.plathome.login.member.exception.ExpiredAccessTokenException;
import com.example.plathome.login.member.exception.ExpiredRefreshTokenException;
import com.example.plathome.login.member.exception.InvalidAccessTokenException;
import com.example.plathome.login.member.exception.InvalidRefreshTokenException;
import com.example.plathome.login.member.service.JwtValidateService;
import com.example.plathome.member.exception.MemberNotFoundException;
import com.example.plathome.member.repository.MemberRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Import({SecurityConfig.class})
@WebMvcTest(LoginController.class)
public class ControllerLoginInit extends ControllerObjectBuilder {
    @MockBean
    protected MemberRepository memberRepository;
    @MockBean
    protected JwtValidateService jwtValidateService;

    protected void login200Init(String secretKey) {
        given(jwtValidateService.validateAccessToken(any())).willReturn(createClaims(secretKey));
        given(jwtValidateService.validateRefreshToken(any())).willReturn(createClaims(secretKey));

        given(memberRepository.findByUserId(any())).willReturn(Optional.ofNullable(createMember()));
    }

    protected void login404Init(String secretKey) {
        given(jwtValidateService.validateAccessToken(any())).willReturn(createClaims(secretKey));
        given(jwtValidateService.validateRefreshToken(any())).willReturn(createClaims(secretKey));
        given(memberRepository.findByUserId(any())).willThrow(MemberNotFoundException.class);
    }

    protected void invalidAccessTokenInit() {
        given(jwtValidateService.validateAccessToken(any())).willThrow(InvalidAccessTokenException.class);
    }

    protected void expiredAccessTokenInit(){
        given(jwtValidateService.validateAccessToken(any())).willThrow(ExpiredAccessTokenException.class);
    }

    protected void invalidRefreshTokenInit() {
        given(jwtValidateService.validateRefreshToken(any())).willThrow(InvalidRefreshTokenException.class);
    }

    protected void expiredRefreshTokenInit(){
        given(jwtValidateService.validateRefreshToken(any())).willThrow(ExpiredRefreshTokenException.class);
    }
}
