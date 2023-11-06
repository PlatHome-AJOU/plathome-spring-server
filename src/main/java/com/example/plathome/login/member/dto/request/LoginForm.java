package com.example.plathome.login.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginForm (
        @Email String userId,
        @NotBlank String password
){
}
