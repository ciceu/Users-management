package com.users.demo.core.domain.exceptions;

public class ConflictException extends RuntimeException {
    private ErrorStatus errorStatus;

    public ConflictException(ErrorStatus errorCode, String message) {
        super(message);
        this.errorStatus = errorCode;
    }
}
