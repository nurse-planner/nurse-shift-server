package com.nurseshift.shift.nurse;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NurseService {

    private final NurseRepository nurseRepository;
    private final MemberService memberService;

    @Transactional
    public Nurse createNurse(Nurse nurse, Member member) {
        Member findMember = memberService.findVerifyMember(member.getId());
        nurse.setMember(findMember);
        boolean isExist = nurseRepository.existsById(nurse.getId());
        if (!isExist) {
            return nurseRepository.save(nurse);
        }

        throw new CustomException(ExceptionCode.ID_DUPLICATE);
    }

    @Transactional
    public Nurse updateNurse(Nurse nurse, Member member) {
        Nurse findNurse = verfiyNurse(nurse.getId());
        System.out.println(findNurse.getMember().getEmail());
        System.out.println(member.getEmail());

        memberService.checkEqualMember(findNurse.getMember(), member);

        Optional.ofNullable(nurse.getRole())
                .ifPresent(findNurse::setRole);
        Optional.ofNullable(nurse.getName())
                .ifPresent(findNurse::setName);
        Optional.ofNullable(nurse.getIsPregnant())
                .ifPresent(findNurse::setIsPregnant);
        Optional.ofNullable(nurse.getPreceptorId())
                .ifPresent(findNurse::setPreceptorId);
        Optional.ofNullable(nurse.getDutyKeep())
                .ifPresent(findNurse::setDutyKeep);

        return nurseRepository.save(findNurse);
    }

    @Transactional(readOnly = true)
    public List<Nurse> getNurses(Member member) {
        Member findMember = memberService.findVerifyMember(member.getId());
        return nurseRepository.findAllByMember(findMember);
    }

    @Transactional(readOnly = true)
    public Nurse getNurse(String id, Member member) {
        memberService.findVerifyMember(member.getId());
        return verfiyNurse(id);
    }

    @Transactional
    public void deleteNurse(String id, Member member) {
        memberService.findVerifyMember(member.getId());
        Nurse findNurse = verfiyNurse(id);
        memberService.checkEqualMember(findNurse.getMember(), member);
        nurseRepository.deleteById(id);
    }

    private Nurse verfiyNurse(String nurse) {
        return nurseRepository.findById(nurse)
                .orElseThrow(() -> new CustomException(ExceptionCode.NURSE_NOT_FOUND));
    }
}
