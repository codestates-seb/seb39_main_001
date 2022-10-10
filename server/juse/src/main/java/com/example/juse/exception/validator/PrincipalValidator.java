package com.example.juse.exception.validator;

import com.example.juse.security.oauth.PrincipalDetails;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PrincipalValidator implements ConstraintValidator<NotEmptyToken, PrincipalDetails> {

    @Override
    public boolean isValid(PrincipalDetails value, ConstraintValidatorContext context) {
        return value != null && value.getSocialUser() != null && value.getSocialUser().getId() != null;
    }

    @Override
    public void initialize(NotEmptyToken constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
