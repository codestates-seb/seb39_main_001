package com.example.juse.exception.response;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

    private int code;
    private String message;

    @Builder.Default
    private List<FieldErrorDto> fieldErrors = new ArrayList<>();

    @Builder.Default
    private List<ConstraintViolationDto> constraintViolations = new ArrayList<>();

    public static ExceptionResponse of(CustomRuntimeException customRuntimeException) {
        return ExceptionResponse.builder()
                .code(customRuntimeException.getExceptionCode().getCode())
                .message(customRuntimeException.getMessage())
                .build();
    }

    public static ExceptionResponse of(BindingResult bindingResult) {
        return ExceptionResponse.builder()
                .code(ExceptionCode.INVALID_METHOD_ARGUMENT.getCode())
                .message(ExceptionCode.INVALID_METHOD_ARGUMENT.getDesc())
                .fieldErrors(FieldErrorDto.of(bindingResult))
                .build();
    }

    public static ExceptionResponse of(Set<ConstraintViolation<?>> constraintViolationSet) {
        return ExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .constraintViolations(ConstraintViolationDto.of(constraintViolationSet))
                .build();

    }


    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class FieldErrorDto {

        private String field;
        private Object rejectedValue;
        private String cause;

        private static List<FieldErrorDto> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream().map(
                    fieldError -> FieldErrorDto.builder()
                            .field(fieldError.getField())
                            .rejectedValue(fieldError.getRejectedValue())
                            .cause(fieldError.getDefaultMessage())
                            .build()
            ).collect(Collectors.toList());
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ConstraintViolationDto {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        //todo exception code를 받아와서 처리할 수 있는 방법?
        private static List<ConstraintViolationDto> of(Set<ConstraintViolation<?>> constraintViolationSet) {
            return constraintViolationSet.stream().map(
                    constraintViolation -> ConstraintViolationDto.builder()
                            .propertyPath(constraintViolation.getPropertyPath().toString())
                            .rejectedValue(constraintViolation.getInvalidValue())
                            .reason(constraintViolation.getMessage())
                            .build()
            ).collect(Collectors.toList());
        }

    }


}
