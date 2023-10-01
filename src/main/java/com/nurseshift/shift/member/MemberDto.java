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
}
