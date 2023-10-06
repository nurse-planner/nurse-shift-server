package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.member.MemberService;
import com.nurseshift.shift.member.authentication.MemberPrincipal;
import com.nurseshift.shift.nurse.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
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
        int year = startDate.getYear();
        int month = startDate.getMonthValue();

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        System.out.println("endOfMonth = " + endOfMonth);
        List<Nurse> nurses = nurseService.getNurses(member);
        List<ScheduleDto.Response> schedules = new ArrayList<>();
        for (Nurse nurse : nurses) {
            List<Schedule> schedule = scheduleRepository.findAllByMemberAndNurseAndDateBetween(member, nurse, startDate, endOfMonth);
            System.out.println("schedule = " + schedule.size());
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
    public void createSchedule(MemberPrincipal memberPrincipal, ScheduleDto.Result[] result, LocalDate startDate) {
        Member member = memberService.findVerifyMember(memberPrincipal.getMember().getId());

        int year = startDate.getYear();
        int month = startDate.getMonthValue();

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Schedule> schedulesToDelete = scheduleRepository.findAllByMemberAndDateBetween(member, startOfMonth, endOfMonth);

        if (!schedulesToDelete.isEmpty()){
            scheduleRepository.deleteAll(schedulesToDelete);
        }

        for (ScheduleDto.Result rs : result) {
            Nurse nurse = nurseService.getNurse(rs.getId(), member);
            int size = rs.getDay().size();
            System.out.println("size    = " + size);
            for (String date : rs.getDay().keySet()) {
                Schedule schedule = new Schedule();
                schedule.setShiftType(rs.getDay().get(date));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date,formatter);
                System.out.println(nurse.getId() + " nurseID " +  localDate + " " + date);
                schedule.setDate(localDate);
                nurse.addSchedule(schedule);
                member.addSchedule(schedule);
                scheduleRepository.save(schedule);
            }
        }
        System.out.println("result.length = " + result.length);
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
