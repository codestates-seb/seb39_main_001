package com.example.juse.exception.validator;

import com.example.juse.exception.ExceptionCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrincipalValidator.class)
public @interface NotEmptyToken {

    String message() default "AUTH_TOKEN_NULL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
