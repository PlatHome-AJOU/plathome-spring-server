package com.example.plathome.login.member.dto.request;

import com.example.plathome.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpForm(
        @NotBlank String username,
        @Email String userId,
        @NotBlank String password
) {
    public static SignUpForm of(String username, String userId, String password) {
        return new SignUpForm(username, userId, password);
    }

    public Member toEntity(PasswordEncoder encoder) {
        return Member.builder()
                .username(this.username)
                .userId(this.userId)
                .password(encoder.encode(this.password)).build();
    }
}
