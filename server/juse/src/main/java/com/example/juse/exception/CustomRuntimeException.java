package com.example.juse.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CustomRuntimeException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDesc());
        this.exceptionCode = exceptionCode;
    }

}
