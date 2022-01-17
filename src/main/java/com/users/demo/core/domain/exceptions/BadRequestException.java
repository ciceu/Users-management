package com.users.demo.core.domain.exceptions;

public class BadRequestException extends RuntimeException {
    private ErrorStatus errorStatus;

    public BadRequestException(ErrorStatus errorCode, String message) {
        super(message);
        this.errorStatus = errorCode;
    }
}
