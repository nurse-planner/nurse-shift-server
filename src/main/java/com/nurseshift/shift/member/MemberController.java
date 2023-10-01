package com.nurseshift.shift.member;

import com.nurseshift.shift.member.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> postMember(@RequestBody MemberDto.Post post) {
        memberService.createMember(memberMapper.requestToEntity(post));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> postMember(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Member verifyMember = memberService.findVerifyMember(memberPrincipal.getMember().getId());
        MemberDto.Response response = memberMapper.entityToResponse(verifyMember);
        return ResponseEntity.ok(response);
    }
}
