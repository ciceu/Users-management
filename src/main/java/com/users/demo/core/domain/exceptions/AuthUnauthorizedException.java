package com.users.demo.core.domain.exceptions;


import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class AuthUnauthorizedException extends OAuth2Exception {

    private ErrorStatus errorStatus;

    public AuthUnauthorizedException(ErrorStatus errorCode, String message){
        super(message);
        this.errorStatus = errorCode;
    }

    @Override
    public String getOAuth2ErrorCode() {
        return errorStatus.getKey();
    }
}
