package com.example.juse.helper.utils;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.security.oauth.PrincipalDetails;

import java.util.Objects;

public class NullCheckUtils {

    public static void checkPrincipalAndThrow(PrincipalDetails principalDetails) {
        if (Objects.isNull(principalDetails)) {
            throw new CustomRuntimeException(ExceptionCode.AUTH_TOKEN_NULL);
        }

        if (principalDetails.getSocialUser() == null) {
            throw new CustomRuntimeException(ExceptionCode.USER_NOT_AUTHENTICATED);
        }
    }
}
