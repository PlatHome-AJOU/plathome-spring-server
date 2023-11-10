package com.example.plathome.controller.helper;

import com.example.plathome.controller.helper.common.ControllerObjectBuilder;
import com.example.plathome.global.config.SecurityConfig;
import com.example.plathome.login.argumentresolver_interceptor.controller.LoginController;
import com.example.plathome.login.argumentresolver_interceptor.service.JwtMemberService;
import com.example.plathome.login.member.domain.SecretKey;
import com.example.plathome.login.member.repository.RefreshTokenRepository;
import com.example.plathome.login.member.service.JwtLoginService;
import com.example.plathome.login.member.service.JwtValidateService;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.member.service.MemberService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({
        SecurityConfig.class,
        JwtValidateService.class,
        SecretKey.class,
        RefreshTokenRepository.class,
        JwtMemberService.class
})
@WebMvcTest(LoginController.class)
public class LoginControllerTestHelper extends ControllerObjectBuilder {
    @MockBean
    protected JwtLoginService jwtLoginService;
    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberRepository memberRepository;
    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;
}
