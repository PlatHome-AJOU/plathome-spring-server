package com.example.plathome.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static com.example.plathome.login.jwt.common.JwtStaticField.ACCESS_HEADER;
import static com.example.plathome.login.jwt.common.JwtStaticField.REFRESH_HEADER;

@OpenAPIDefinition(
        info = @Info(title = "PlatHome API 명세서",
                description = "User-Service API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .name(ACCESS_HEADER)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .name(REFRESH_HEADER)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("accessToken")
                .addList("refreshToken");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("accessToken", accessTokenScheme)
                        .addSecuritySchemes("refreshToken", refreshTokenScheme))
                .security(Arrays.asList(securityRequirement));
    }
}
