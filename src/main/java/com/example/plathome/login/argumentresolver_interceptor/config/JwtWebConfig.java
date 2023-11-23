package com.example.plathome.login.argumentresolver_interceptor.config;


import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.JwtAdminArgumentResolver;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.JwtLoginArgumentResolver;
import com.example.plathome.login.argumentresolver_interceptor.interceptor.JwtLoginCheckInterceptor;
import com.example.plathome.login.argumentresolver_interceptor.service.JwtMemberService;
import com.example.plathome.login.jwt.service.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtWebConfig implements WebMvcConfigurer {

    private final JwtMemberService jwtMemberService;
    private final JwtValidateService jwtValidateService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtLoginCheckInterceptor(jwtMemberService, jwtValidateService))
                .order(1)
                .addPathPatterns("/api/jwt/**", "/api/requested/**")
                .excludePathPatterns("/api/jwt/sign-up", "/api/jwt/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtLoginArgumentResolver());
        resolvers.add(new JwtAdminArgumentResolver());
    }
}
