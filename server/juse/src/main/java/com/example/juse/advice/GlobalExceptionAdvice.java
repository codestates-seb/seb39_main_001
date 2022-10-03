package com.example.juse.advice;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(CustomRuntimeException.class)
    public ExceptionResponse handleCustomRuntimeException(CustomRuntimeException customRuntimeException) {

        return ExceptionResponse.of(customRuntimeException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        return ExceptionResponse.of(exception.getBindingResult());
    }
}
