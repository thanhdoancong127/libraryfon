package com.ns.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.ns.common.viewmodel.ErrorVm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ApiAbstractExceptionHandler {
    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorVm handleNotFoundException(NotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorVm errorVm = new ErrorVm("" + HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.NOT_FOUND.value(), message);
        log.debug(ex.toString());
        return errorVm;
    }

    @ExceptionHandler({ SignInRequiredException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorVm handleSignInRequired(SignInRequiredException ex) {
        String message = ex.getMessage();
        return new ErrorVm(HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message);
    }

    @ExceptionHandler({ Forbidden.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorVm handleForbidden(NotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorVm errorVm = new ErrorVm(HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN.getReasonPhrase(), message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), HttpStatus.FORBIDDEN.value(), message);
        log.debug(ex.toString());
        return errorVm;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVm handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .toList();

        ErrorVm errorVm = new ErrorVm(HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request information is not valid", errors);
        return errorVm;
    }

    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }
}
