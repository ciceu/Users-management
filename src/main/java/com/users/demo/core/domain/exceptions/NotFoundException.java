package com.users.demo.core.domain.exceptions;

public class NotFoundException extends RuntimeException{
    private ErrorStatus errorStatus;

    public NotFoundException(ErrorStatus errorCode, String message) {
        super(message);
        this.errorStatus = errorCode;
    }
}
