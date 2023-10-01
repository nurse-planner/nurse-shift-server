package com.nurseshift.shift.common.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurseshift.shift.common.exception.ExceptionResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException | UsernameNotFoundException exception) {
            setErrorResponse(response, exception);
        }
    }

    private void setErrorResponse(HttpServletResponse response, RuntimeException exception) throws IOException {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.UNAUTHORIZED, exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse);
        response.getWriter().println(objectMapper);
    }
}
