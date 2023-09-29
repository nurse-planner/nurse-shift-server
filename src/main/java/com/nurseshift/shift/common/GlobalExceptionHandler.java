package com.nurseshift.shift.common;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(CustomException customException) {
        ExceptionResponse response = ExceptionResponse.of(customException.getExceptionCode());
        return ResponseEntity.status(customException.getExceptionCode().getStatus()).body(response);
    }
}
