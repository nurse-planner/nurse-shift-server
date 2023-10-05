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
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberService memberService;
    private final NurseService nurseService;

    @Transactional(readOnly = true)
    public List<ScheduleDto.Response> getSchedules(MemberPrincipal memberPrincipal, LocalDate startDate) {
        Member member = memberService.findVerifyMember(memberPrincipal.getMember().getId());
        LocalDate end = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Nurse> nurses = nurseService.getNurses(member);
        List<ScheduleDto.Response> schedules = new ArrayList<>();
        for (Nurse nurse : nurses) {
            List<Schedule> schedule = scheduleRepository.findAllByMemberAndNurseAndDateBetween(member, nurse, startDate, end);
            schedules.add(new ScheduleDto.Response(nurse, schedule));
        }

        return schedules;
    }

    public List<Integer> getMonths(MemberPrincipal memberPrincipal){
        return scheduleRepository.findDistinctMonths();
    }

    @Transactional
    public void createSchedule(MemberPrincipal memberPrincipal) {
        Member member = memberService.findVerifyMember(memberPrincipal.getMember().getId());
        // TODO: post로 들어오는 값들로 간호사 전처리 후 파이썬으로 근무표 생성하고 받아오기
        List<Nurse> nurses = nurseService.getNurses(member);
        Random random = new Random();
        LocalDate now = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        int startDay = now.getDayOfMonth();
        for (int day = 0; day <= lastDayOfMonth - startDay; day++) {
            for (Nurse nurse : nurses) {
                Schedule schedule = new Schedule();
                schedule.setDate(LocalDate.now().plusDays(day));
                int i = random.nextInt(3) + 1;
                schedule.setShiftType(i == 3 ? "N" : i == 2 ? "E" : "D");
                nurse.addSchedule(schedule);
                member.addSchedule(schedule);
                scheduleRepository.save(schedule);
            }
        }
        // TODO: 받아온 근무표로 Schedule 생성 및 간호사와 매핑하여 DB 저장하기
    }
}
