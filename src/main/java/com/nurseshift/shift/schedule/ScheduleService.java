package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.MemberService;
import com.nurseshift.shift.member.authentication.MemberPrincipal;
import com.nurseshift.shift.nurse.Nurse;
import com.nurseshift.shift.nurse.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberService memberService;
    private final NurseService nurseService;

    public List<Schedule> getSchedules(MemberPrincipal memberPrincipal, LocalDate startDate) {
        Member member = memberService.findVerifyMember(memberPrincipal.getMember().getId());
        List<Schedule> schedules = scheduleRepository.findAllByMemberAndIdxAndDate(member, startDate);
        return schedules;
    }

    @Transactional
    public void createSchedule(ScheduleDto.Post post, MemberPrincipal memberPrincipal) {
        Member member = memberService.findVerifyMember(memberPrincipal.getMember().getId());
        // TODO: post로 들어오는 값들로 간호사 전처리 후 파이썬으로 근무표 생성하고 받아오기
        List<Nurse> nurses = nurseService.getNurses(member);
        for (Nurse nurse : nurses) {
            Schedule schedule = new Schedule();
            schedule.setDate(LocalDate.now());
            schedule.setShiftType("AAA");
            schedule.setTitle("TTTT");
            schedule.setIdx(1L);
            nurse.addSchedule(schedule);
            member.addSchedule(schedule);
            scheduleRepository.save(schedule);
        }
        // TODO: 받아온 근무표로 Schedule 생성 및 간호사와 매핑하여 DB 저장하기
    }
}
