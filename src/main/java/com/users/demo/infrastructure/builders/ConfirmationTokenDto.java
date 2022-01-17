package com.users.demo.infrastructure.builders;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConfirmationTokenDto {
    private String token;
}
