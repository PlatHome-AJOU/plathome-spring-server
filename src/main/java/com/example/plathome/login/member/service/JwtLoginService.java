package com.example.plathome.login.member.service;



import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.login.member.dto.request.LoginForm;
import com.example.plathome.login.member.dto.MemberWithTokenDto;
import com.example.plathome.login.member.dto.request.SignUpForm;
import com.example.plathome.member.exception.MemberNotFoundException;
import com.example.plathome.member.exception.MemberPasswordNotMatchException;
import com.example.plathome.login.member.provider.JwtProvider;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.member.service.JwtMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtLoginService {
    private final JwtMemberService jwtMemberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public MemberWithTokenDto signUp(SignUpForm signUpForm) {
        return jwtMemberService.save(signUpForm);
    }

    public MemberWithTokenDto login(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberRepository.findByUserId(loginForm.userId()).orElseThrow(MemberNotFoundException::new);
        if (passwordEncoder.matches(loginForm.password(), member.getPassword())) {
            String accessToken = jwtProvider.createAccessToken(loginForm.userId());
            String refreshToken = jwtProvider.createRefreshToken(loginForm.userId());
            jwtProvider.setAuthorizationHeader(response, accessToken);
            return MemberWithTokenDto.from(member, refreshToken);
        }
        throw new MemberPasswordNotMatchException();
    }

    public void refresh(MemberSession memberSession, HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(memberSession.userId());
        jwtProvider.setAuthorizationHeader(response, accessToken);
    }

    public void logout(MemberSession memberSession) {
        jwtProvider.expiredRefreshToken(memberSession.userId());
    }
}
