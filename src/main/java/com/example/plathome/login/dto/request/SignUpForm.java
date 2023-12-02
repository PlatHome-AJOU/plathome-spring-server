package com.example.plathome.login.dto.request;

import com.example.plathome.login.dto.request.annotation.AjouEmail;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.types.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record SignUpForm(
        @NotBlank @Size(min = 6, max = 6) String authCode,
        @NotBlank @Size(max = 20) String nickname,
        @AjouEmail @Size(max = 100) String email,
        @NotBlank @Size(max = 32) String password
) {

    public static SignUpFormBuilder of() {
        return SignUpForm.builder();
    }

    public Member toEntity(PasswordEncoder encoder) {
        return Member.of()
                .nickname(this.nickname())
                .email(this.email())
                .password(encoder.encode(this.password()))
                .roleType(RoleType.USER).build();
    }
}
