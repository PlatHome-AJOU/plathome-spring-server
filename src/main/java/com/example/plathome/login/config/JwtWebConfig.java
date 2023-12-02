package com.example.plathome.login.config;


import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.JwtAdminArgumentResolver;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.JwtLoginArgumentResolver;
import com.example.plathome.login.argumentresolver_interceptor.interceptor.JwtLoginCheckInterceptor;
import com.example.plathome.login.service.JwtValidateService;
import com.example.plathome.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtWebConfig implements WebMvcConfigurer {

    private final MemberService memberService;
    private final JwtValidateService jwtValidateService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtLoginCheckInterceptor(memberService, jwtValidateService))
                .order(1)
                .addPathPatterns(
                        "/api/jwt/auth/**",
                        "/api/email/auth/**",
                        "/api/requested/auth/**",
                        "/api/estate/auth/**",
                        "/api/member/auth/**",
                        "/api/chat/user-report/auth/**",
                        "/api/estate-report/auth/**",
                        "/api/wish-list/auth/**",
                        "/api/schedule/auth/**"
                )
                .excludePathPatterns(
                        "/api/jwt/no-auth/**",
                        "/api/email/no-auth/**",
                        "/api/requested/no-auth/**",
                        "/api/estate/no-auth/**",
                        "/api/member/no-auth/**",
                        "/api/chat/user-report/no-auth/**",
                        "/api/estate-report/no-auth/**",
                        "/api/wish-list/no-auth/**",
                        "/api/schedule/no-auth/**"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtLoginArgumentResolver());
        resolvers.add(new JwtAdminArgumentResolver());
    }
}
