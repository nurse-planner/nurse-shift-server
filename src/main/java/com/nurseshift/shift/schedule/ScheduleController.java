package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public ResponseEntity<?> postSchedule(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        scheduleService.createSchedule(memberPrincipal);
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto.Response>> getSchedule(@AuthenticationPrincipal MemberPrincipal principal,
                                                                  @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate) {
        List<ScheduleDto.Response> responses = scheduleService.getSchedules(principal, startDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Integer>> getSchedule(@AuthenticationPrincipal MemberPrincipal principal) {
        List<Integer> months = scheduleService.getMonths(principal);
        return ResponseEntity.ok(months);
    }
}
