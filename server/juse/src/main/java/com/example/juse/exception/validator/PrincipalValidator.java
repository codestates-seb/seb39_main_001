package com.example.juse.exception.validator;

import com.example.juse.exception.ExceptionCode;
import com.example.juse.security.oauth.PrincipalDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class PrincipalValidator implements ConstraintValidator<NotEmptyToken, PrincipalDetails> {

    @Override
    public boolean isValid(PrincipalDetails value, ConstraintValidatorContext context) {
        return value != null && value.getSocialUser() != null;
    }

    @Override
    public void initialize(NotEmptyToken constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
