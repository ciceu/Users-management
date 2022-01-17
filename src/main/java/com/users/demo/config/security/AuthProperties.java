package com.users.demo.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties("auth")
public class AuthProperties {

    private String jwtSigningKey;
    private String corsAllowedOrigins;
}
