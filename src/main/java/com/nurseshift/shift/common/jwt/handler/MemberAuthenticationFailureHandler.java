package com.nurseshift.shift.common.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurseshift.shift.common.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
        log.info("# Authentication fail");
        String errorMessage = getErrorMessage(exception);
        sendErrorResponse(response, errorMessage);
    }

    private String getErrorMessage(AuthenticationException exception) {
        System.out.println(exception);
        if (exception instanceof UsernameNotFoundException) return "계정이 존재하지 않습니다. 회원가입 후 로그인 해주세요.";
        if (exception instanceof BadCredentialsException) return "아이디 또는 비밀번호가 일치하지 않습니다.";
        return "알 수 없는 이유로 로그인에 실패했습니다. 관리자에게 문의하세요.";
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.UNAUTHORIZED, errorMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String rsp = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse);
        response.getWriter().println(rsp);
    }
}
