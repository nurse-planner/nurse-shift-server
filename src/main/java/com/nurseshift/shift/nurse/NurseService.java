package com.nurseshift.shift.nurse;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.MemberService;
import com.nurseshift.shift.nurse.off.Off;
import com.nurseshift.shift.nurse.off.OffService;
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
    private final OffService offService;

    @Transactional
    public Nurse createNurse(Nurse nurse, Member member, List<Off> offs, List<Off> rests) {
        Member findMember = memberService.findVerifyMember(member.getId());
        findMember.addNurse(nurse);
        Nurse saved = nurseRepository.save(nurse);
        offs.addAll(rests);
        offService.createOff(offs, saved);
        return saved;
    }

    @Transactional
    public Nurse updateNurse(Nurse nurse, Member member, List<Off> offs, List<Off> rests) {
        Nurse findNurse = verfiyNurse(nurse.getId());
        memberService.checkEqualMember(findNurse.getMember(), member);
        offService.deleteOff(findNurse);
        offs.addAll(rests);
        offService.createOff(offs, nurse);
        Optional.ofNullable(nurse.getWorkingYear())
                .ifPresent(findNurse::setWorkingYear);
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
    public Nurse getNurse(Long id, Member member) {
        memberService.findVerifyMember(member.getId());
        return verfiyNurse(id);
    }

    @Transactional
    public void deleteNurse(Long id, Member member) {
        memberService.findVerifyMember(member.getId());
        Nurse findNurse = verfiyNurse(id);
        memberService.checkEqualMember(findNurse.getMember(), member);
        nurseRepository.deleteById(id);
    }

    private Nurse verfiyNurse(Long id) {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NURSE_NOT_FOUND));
    }
}
