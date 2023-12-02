package com.example.plathome.login.service;


import com.example.plathome.email.service.MailMemberService;
import com.example.plathome.login.domain.UserContext;
import com.example.plathome.login.dto.request.LoginForm;
import com.example.plathome.login.dto.request.SignUpForm;
import com.example.plathome.login.dto.response.MemberResponse;
import com.example.plathome.login.dto.response.TokenResponse;
import com.example.plathome.login.provider.JwtProvider;
import com.example.plathome.login.service.redis.RefreshTokenRedisService;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.exception.NotMatchMemberPasswordException;
import com.example.plathome.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_DUPLICATE_EMAIL;
import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_DUPLICATE_NICKNAME;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final MailMemberService mailMemberService;

    @Transactional
    public MemberResponse signUp(SignUpForm signUpForm) {
        verifyValidSignUpdForm(signUpForm);
        mailMemberService.verifyCode(signUpForm.email(), signUpForm.authCode());
        UserContext.set(signUpForm.nickname());
        return MemberResponse.from(memberRepository.save(signUpForm.toEntity(passwordEncoder)));
    }

    private void verifyValidSignUpdForm(SignUpForm signUpForm) {
        String email = signUpForm.email();
        String nickname = signUpForm.nickname();
        Optional<Member> optionalUser = memberRepository.findByEmailOrNickname(email, nickname);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getEmail().equals(email)) {
                throw new DuplicationMemberException(ERROR_MEMBER_DUPLICATE_EMAIL);
            } else {
                throw new DuplicationMemberException(ERROR_MEMBER_DUPLICATE_NICKNAME);
            }
        }
    }

    public TokenResponse login(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        String email = loginForm.email();
        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);
        if (passwordEncoder.matches(loginForm.password(), member.getPassword())) {
            String accessToken = jwtProvider.createAccessToken(member.getId().toString());
            String refreshToken = jwtProvider.createRefreshToken(member.getId().toString());
            refreshTokenRedisService.setData(member.getId().toString(), refreshToken);

            return TokenResponse.from(accessToken, refreshToken);
        }
        throw new NotMatchMemberPasswordException();
    }

    public TokenResponse refresh(MemberSession memberSession) {
        String accessToken = jwtProvider.createAccessToken(String.valueOf(memberSession.id()));
        String refreshToken = jwtProvider.createRefreshToken(String.valueOf(memberSession.id()));
        refreshTokenRedisService.setData(String.valueOf(memberSession.id()), refreshToken);

        return TokenResponse.from(accessToken, refreshToken);
    }

    public void logout(MemberSession memberSession) {
        refreshTokenRedisService.deleteData(memberSession.email());
    }
}
