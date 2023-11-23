package com.example.plathome.login.argumentresolver_interceptor.controller;


import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.login.jwt.dto.request.LoginForm;
import com.example.plathome.login.jwt.dto.response.MemberWithTokenResponse;
import com.example.plathome.login.jwt.dto.request.SignUpForm;
import com.example.plathome.login.jwt.service.JwtLoginService;
import com.example.plathome.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
public class LoginController {

    private final JwtLoginService jwtLoginService;
    private final MemberService memberService;

    @GetMapping("/token")
    public MemberWithTokenResponse refresh(
            @Login MemberSession memberSession,
            HttpServletResponse response) {
        return MemberWithTokenResponse.from(jwtLoginService.refresh(memberSession, response));
    }

    @GetMapping("/get")
    public MemberWithTokenResponse get(@Login MemberSession memberSession) {
        return MemberWithTokenResponse.from(memberService.getBySession(memberSession));
    }

    @PostMapping("/sign-up")
    public MemberWithTokenResponse signUp(@RequestBody @Valid SignUpForm signUpForm) {
        return MemberWithTokenResponse.withoutToken(jwtLoginService.signUp(signUpForm));
    }

    @PostMapping("/login")
    public MemberWithTokenResponse login(
            @RequestBody @Valid LoginForm loginForm,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        MemberWithTokenResponse memberWithTokenResponse = MemberWithTokenResponse.from(jwtLoginService.login(loginForm, request, response));
        return memberWithTokenResponse;
    }

    @GetMapping("/logout")
    public String logout(@Login MemberSession memberSession, HttpServletRequest request) {
        jwtLoginService.logout(memberSession);
        return "success";
    }
}
