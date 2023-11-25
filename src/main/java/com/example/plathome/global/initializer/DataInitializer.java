package com.example.plathome.global.initializer;

import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.constant.RoleType;
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

    @Value("${member.test.username}")
    private String test_username;
    @Value("${member.test.user-id}")
    private String test_userId;
    @Value("${member.test.raw-password}")
    private String test_password;
    @Value("${member.admin.username}")
    private String admin_username;
    @Value("${member.admin.user-id}")
    private String admin_userId;
    @Value("${member.admin.raw-password}")
    private String admin_password;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Member tester = Member.of()
                .username(test_username)
                .userId(test_userId)
                .password(passwordEncoder.encode(test_password))
                .roleType(RoleType.USER)
                .createdBy(test_username)
                .modifiedBy(test_username).build();

        Member admin = Member.of()
                .username(admin_username)
                .userId(admin_userId)
                .password(passwordEncoder.encode(admin_password))
                .roleType(RoleType.ADMIN)
                .createdBy(admin_username)
                .modifiedBy(admin_username).build();

        if (memberRepository.findByUserId(admin_userId).isEmpty()) {
            memberRepository.save(admin);
        }
        if (memberRepository.findByUserId(test_userId).isEmpty()) {
            memberRepository.save(tester);
        }
    }
}
