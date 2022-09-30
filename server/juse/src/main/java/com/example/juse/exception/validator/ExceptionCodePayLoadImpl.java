package com.example.juse.exception.validator;

import com.example.juse.exception.ExceptionCode;

public class ExceptionCodePayLoadImpl implements ExceptionCodePayLoad {

    @Override
    public int getExceptionCode(String value) {
        return ExceptionCode.valueOf(value.toUpperCase()).getCode();
    }
}
