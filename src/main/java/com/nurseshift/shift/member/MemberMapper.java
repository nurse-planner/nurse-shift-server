package com.nurseshift.shift.member;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberMapper {

    public Member requestToEntity(MemberDto.Post post) {
        return new Member(post.getEmail(), post.getPassword());
    }
}
