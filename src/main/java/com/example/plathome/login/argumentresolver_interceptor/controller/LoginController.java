package com.example.plathome.login.argumentresolver_interceptor.controller;


import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.login.jwt.dto.request.LoginForm;
import com.example.plathome.login.jwt.dto.request.SignUpForm;
import com.example.plathome.login.jwt.dto.response.MemberResponse;
import com.example.plathome.login.jwt.dto.response.TokenResponse;
import com.example.plathome.login.jwt.service.JwtLoginService;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@RestController
public class LoginController {

    private final JwtLoginService jwtLoginService;
    private final MemberService memberService;

    @GetMapping("/auth/token")
    public TokenResponse refresh(
            @Login MemberSession memberSession,
            HttpServletResponse response) {
        return TokenResponse.from(jwtLoginService.refresh(memberSession, response));
    }

    @GetMapping("/auth")
    public MemberResponse get(@Login MemberSession memberSession) {
        return MemberResponse.from(memberService.getBySession(memberSession));
    }

    @PostMapping("/no-auth/sign-up")
    public MemberResponse signUp(@RequestBody @Valid SignUpForm signUpForm) {
        return MemberResponse.from(jwtLoginService.signUp(signUpForm));
    }

    @PostMapping("/no-auth/login")
    public TokenResponse login(
            @RequestBody @Valid LoginForm loginForm,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        return TokenResponse.from(jwtLoginService.login(loginForm, request, response));
    }

    @GetMapping("/auth/logout")
    public String logout(@Login MemberSession memberSession, HttpServletRequest request) {
        jwtLoginService.logout(memberSession);
        return "success";
    }
}
