package com.example.plathome.global.config;

import com.example.plathome.login.jwt.domain.UserContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(UserContext.get());
    }

}
