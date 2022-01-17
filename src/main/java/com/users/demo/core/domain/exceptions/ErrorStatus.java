package com.users.demo.core.domain.exceptions;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    UNAUTHORIZED("UNAUTHORIZED", 401),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 400),
    EMAIL_ADDRESS_ALREADY_EXISTS("EMAIL_ADDRESS_ALREADY_EXISTS", 409),
    LINK_EXPIRED("LINK_EXPIRED", 400),
    INVALID_FIELDS("INVALID_FIELDS", 400),
    ACCOUNT_NOT_ENABLED("ACCOUNT_NOT_ENABLED", 403);

    private final String key;
    private final Integer status;

    ErrorStatus(String key, Integer status){
        this.key = key;
        this.status = status;
    }
}
