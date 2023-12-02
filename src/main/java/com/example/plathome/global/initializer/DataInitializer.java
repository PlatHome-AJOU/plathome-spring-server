package com.example.plathome.global.initializer;

import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.types.RoleType;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${member.test.nickname}")
    private String test_nickname;
    @Value("${member.test.email}")
    private String test_email;
    @Value("${member.test.raw-password}")
    private String test_password;
    @Value("${member.admin.nickname}")
    private String admin_nickname;
    @Value("${member.admin.email}")
    private String admin_email;
    @Value("${member.admin.raw-password}")
    private String admin_password;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (memberRepository.findByEmail(test_email).isEmpty()) {
            Member tester = Member.of()
                    .nickname(test_nickname)
                    .email(test_email)
                    .password(passwordEncoder.encode(test_password))
                    .roleType(RoleType.USER)
                    .createdBy(test_nickname)
                    .modifiedBy(test_nickname).build();
            memberRepository.save(tester);
        }

        if (memberRepository.findByEmail(admin_email).isEmpty()) {
            Member admin = Member.of()
                    .nickname(admin_nickname)
                    .email(admin_email)
                    .password(passwordEncoder.encode(admin_password))
                    .roleType(RoleType.ADMIN)
                    .createdBy(admin_nickname)
                    .modifiedBy(admin_nickname).build();
            memberRepository.save(admin);
        }
    }
}

