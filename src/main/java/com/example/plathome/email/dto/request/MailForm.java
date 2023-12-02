package com.example.plathome.email.dto.request;

import com.example.plathome.login.dto.request.annotation.AjouEmail;
import lombok.Builder;

@Builder
public record MailForm(
        @AjouEmail String email
) {

    public static MailFormBuilder of() {
        return MailForm.builder();
    }
}
