package com.nurseshift.shift.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    /* 409 CONFLICT : 중복된 데이터가 존재 */
    ID_DUPLICATE(HttpStatus.CONFLICT, "Id Is Duplicate");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
