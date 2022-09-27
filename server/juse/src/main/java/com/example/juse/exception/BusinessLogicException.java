package com.example.juse.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDesc());
        this.exceptionCode = exceptionCode;
    }

}
