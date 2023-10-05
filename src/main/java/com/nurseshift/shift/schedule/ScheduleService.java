package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.MemberService;
import com.nurseshift.shift.member.authentication.MemberPrincipal;
import com.nurseshift.shift.nurse.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<ScheduleDto.BaseResponse> getMonths(MemberPrincipal memberPrincipal) {
        List<Object[]> yearMonths = scheduleRepository.findDistinctYearMonths();
        List<ScheduleDto.BaseResponse> baseResponses = yearMonths.stream()
                .map(yearMonth -> {
                    int year = (int) yearMonth[0];
                    int month = (int) yearMonth[1];

                    YearMonth ym = YearMonth.of(year, month);
                    int lastDayOfMonth = ym.lengthOfMonth();

                    LocalDate startDate = LocalDate.of(year, month, 1);
                    LocalDate endDate = LocalDate.of(year, month, lastDayOfMonth);
                    Long count = scheduleRepository.countDistinctNursesByYearAndMonth(year, month, memberPrincipal.getMember().getId());
                    return new ScheduleDto.BaseResponse(startDate, endDate, count);
                })
                .collect(Collectors.toList());
        return baseResponses;
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

    public ScheduleDto.PreResponse getRequestData(MemberPrincipal principal, ScheduleDto.Post post) {
        Member verifyMember = memberService.findVerifyMember(principal.getMember().getId());

        List<Nurse> nurses = nurseService.getNurses(verifyMember);
        List<NurseDto.PretreatmentResponse> chargeNurses = nurses.stream().filter(nurse -> nurse.getRole() == 0 || nurse.getRole() == 1).map(NurseDto.PretreatmentResponse::new).collect(Collectors.toList());
        List<NurseDto.PretreatmentResponse> actNurses = nurses.stream().filter(nurse -> nurse.getRole() == 2).map(NurseDto.PretreatmentResponse::new).collect(Collectors.toList());

        LocalDate startDate = post.getStartDate();
        YearMonth yearMonth = YearMonth.of(startDate.getYear(), startDate.getMonthValue());
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        int startDay = startDate.getDayOfMonth();

        ScheduleDto.PreResponse response = new ScheduleDto.PreResponse(post, lastDayOfMonth - startDay, chargeNurses, actNurses);
        System.out.println(response);
        return response;
    }
}
