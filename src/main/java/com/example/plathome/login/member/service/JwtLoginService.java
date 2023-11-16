package com.example.plathome.login.member.service;


import com.example.plathome.login.member.domain.UserContext;
import com.example.plathome.login.member.dto.MemberWithTokenDto;
import com.example.plathome.login.member.dto.request.LoginForm;
import com.example.plathome.login.member.dto.request.SignUpForm;
import com.example.plathome.login.member.provider.JwtProvider;
import com.example.plathome.login.member.service.redis.RefreshTokenRedisService;
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

import static com.example.plathome.login.member.common.JwtStaticField.BEARER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtLoginService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public MemberWithTokenDto signUp(SignUpForm signUpForm) {
        validDupUserId(signUpForm.userId());
        UserContext.set(signUpForm.username());
        return MemberWithTokenDto.withoutToken(memberRepository.save(signUpForm.toEntity(passwordEncoder)));
    }

    private void validDupUserId(String userId) {
        Optional<Member> optionalUser = memberRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            throw new DuplicationMemberException();
        }
    }

    public MemberWithTokenDto login(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        String userId = loginForm.userId();
        Member member = memberRepository.findByUserId(userId).orElseThrow(NotFoundMemberException::new);
        if (passwordEncoder.matches(loginForm.password(), member.getPassword())) {
            String accessToken = jwtProvider.createAccessToken(userId);
            String refreshToken = jwtProvider.createRefreshToken(userId);
            refreshTokenRedisService.setData(userId, refreshToken);

            return MemberWithTokenDto.from(member, accessToken, refreshToken);
        }
        throw new NotMatchMemberPasswordException();
    }

    public MemberWithTokenDto refresh(MemberSession memberSession, HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(memberSession.userId());
        String refreshToken = jwtProvider.createRefreshToken(memberSession.userId());
        refreshTokenRedisService.setData(memberSession.userId(), refreshToken);

        return MemberWithTokenDto.of()
                .username(memberSession.username())
                .userId(memberSession.userId())
                .accessToken(BEARER + accessToken)
                .refreshToken(BEARER + refreshToken)
                .build();
    }

    public void logout(MemberSession memberSession) {
        refreshTokenRedisService.deleteData(memberSession.userId());
    }
}
