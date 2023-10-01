package com.nurseshift.shift.common.jwt.handler;

import com.nurseshift.shift.common.jwt.JwtTokenProvider;
import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("# Authenticated success");
        MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Member member = memberPrincipal.getMember();

        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail(), member.getRoles());
        jwtTokenProvider.sendAccessToken(response, accessToken);
    }
}