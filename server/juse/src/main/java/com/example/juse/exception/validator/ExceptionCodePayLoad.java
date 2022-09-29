package com.example.juse.exception.validator;

import javax.validation.Payload;

public interface ExceptionCodePayLoad extends Payload {

    int getExceptionCode(String value);

}
