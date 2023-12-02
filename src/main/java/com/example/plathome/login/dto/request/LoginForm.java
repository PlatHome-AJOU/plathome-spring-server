package com.example.plathome.login.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record LoginForm (
        @Email String email,
        @NotBlank String password
){
    public static LoginFormBuilder of() {
        return LoginForm.builder();
    }
}
