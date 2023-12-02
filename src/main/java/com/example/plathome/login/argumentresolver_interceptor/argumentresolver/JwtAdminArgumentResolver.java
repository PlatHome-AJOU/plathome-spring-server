package com.example.plathome.login.argumentresolver_interceptor.argumentresolver;

import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.domain.types.RoleType;
import com.example.plathome.member.exception.ForbiddenMemberException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class JwtAdminArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("JwtAdminArgumentResolver supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Admin.class);
        boolean hasMemberSessionType = MemberSession.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasMemberSessionType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("JwtAdminArgumentResolver resolveArgument 실행");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Object memberSession = request.getAttribute("MemberSession");
        checkAdminRole((MemberSession) memberSession);
        return memberSession;
    }

    private void checkAdminRole(MemberSession memberSession) {
        if (!memberSession.roleType().equals(RoleType.ADMIN)) {
            throw new ForbiddenMemberException();
        }
    }
}
