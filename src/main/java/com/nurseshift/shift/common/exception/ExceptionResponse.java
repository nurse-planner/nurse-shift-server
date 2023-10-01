package com.nurseshift.shift.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class ExceptionResponse implements Serializable {

    private final HttpStatus status;
    private final String message;

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return new ExceptionResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

    public static ExceptionResponse of(HttpStatus status, String message) {
        return new ExceptionResponse(status, message);
    }
}
