package com.example.plathome.login.member.dto.request;

import com.example.plathome.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record SignUpForm(
        @NotBlank @Size(max = 20) String username,
        @Email @Size(max = 100) String userId,
        @NotBlank @Size(max = 32) String password
) {

    public static SignUpFormBuilder of() {
        return SignUpForm.builder();
    }

    public Member toEntity(PasswordEncoder encoder) {
        return Member.of()
                .username(this.username)
                .userId(this.userId)
                .password(encoder.encode(this.password))
                .createdBy(this.username)
                .modifiedBy(this.username)
                .build();
    }
}
