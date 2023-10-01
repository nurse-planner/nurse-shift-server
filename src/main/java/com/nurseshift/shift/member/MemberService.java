package com.nurseshift.shift.member;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        member.setRoles(List.of("ROLE_USER"));
        return memberRepository.save(member);
    }

    public void verifyExistEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
        }
    }

    public Member findVerifyMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.ID_DUPLICATE));
    }


    public void checkEqualMember(Member member, Member principal) {
        if (!member.getEmail().equals(principal.getEmail())) {
            throw new CustomException(ExceptionCode.MEMBER_BAD_REQUEST);
        }
    }
}
