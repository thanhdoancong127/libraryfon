package com.ns.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.ns.common.exception.SignInRequiredException;

public class AuthenticationUtils {
    // public static String getCurrentUserId() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //     if (authentication instanceof AnonymousAuthenticationToken) {
    //         throw new SignInRequiredException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    //     }

    //     JwtAuthenticationToken contextHolder = (JwtAuthenticationToken) authentication;

    //     return contextHolder.getToken().getSubject();
    // }
}