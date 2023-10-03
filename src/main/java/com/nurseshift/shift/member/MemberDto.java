package com.nurseshift.shift.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private String email;
        private String password;
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String email;

        public Response(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
        }
    }

    @Getter
    public static class JwtResponse {
        private final String jwt;
        private final Response user;

        public JwtResponse(String jwt, Member member) {
            this.jwt = jwt;
            this.user = new Response(member);
        }
    }
}
