package com.users.demo.infrastructure.builders;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CredentialsDto {
    private String username;
    private String password;
    private String confirmedPassword;
}
