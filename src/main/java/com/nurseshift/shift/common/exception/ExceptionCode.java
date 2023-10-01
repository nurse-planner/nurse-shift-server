package com.nurseshift.shift.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    /* 400 BAD REQUEST : 잘못된 요청 */
    MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Member Bad Request"),

    /* 404 CONFLICT : 데이터를 찾을 수 없음 */
    NURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "Nurse Not Found"),

    /* 409 CONFLICT : 중복된 데이터가 존재 */
    ID_DUPLICATE(HttpStatus.CONFLICT, "Id Is Duplicate"),
    MEMBER_EMAIL_EXIST(HttpStatus.CONFLICT, "Email Is Exist");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
