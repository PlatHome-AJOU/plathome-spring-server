package com.example.plathome.login.argumentresolver_interceptor.interceptor;


import com.example.plathome.login.argumentresolver_interceptor.service.JwtMemberService;
import com.example.plathome.login.jwt.domain.UserContext;
import com.example.plathome.login.jwt.service.JwtValidateService;
import com.example.plathome.member.domain.MemberSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.plathome.login.jwt.common.JwtStaticField.REFRESH_URL;


@Slf4j
@RequiredArgsConstructor
public class JwtLoginCheckInterceptor implements HandlerInterceptor {

    private final JwtMemberService jwtMemberService;
    private final JwtValidateService jwtValidateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String requestURI = request.getRequestURI();
        log.info("MEMBER 로그인 인증 인터 셉터 실행 [{}]", requestURI);
        String userId = this.getUserId(request);
        MemberSession memberSession = jwtMemberService.getMemberSessionByUserId(userId);
        request.setAttribute("MemberSession", memberSession);
        UserContext.set(memberSession.username());
        log.info("MEMBER 로그인 확인 성공");
        return true;
    }

    private String getUserId(HttpServletRequest request){
        if (!request.getRequestURI().contains(REFRESH_URL)) {
            return jwtValidateService.validateAccessToken(request);
        }
        return jwtValidateService.validateRefreshToken(request);
    }
}
