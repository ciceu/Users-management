package com.users.demo.infrastructure.builders;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuccessStatusDto {

    private String status;
    private String message;

}
