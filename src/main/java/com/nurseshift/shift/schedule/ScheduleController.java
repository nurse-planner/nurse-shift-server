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
    public ResponseEntity<?> postSchedule(@AuthenticationPrincipal MemberPrincipal memberPrincipal, @RequestBody ScheduleDto.Post post) {
        scheduleService.createSchedule(post, memberPrincipal);
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto.Response>> getSchedule(@AuthenticationPrincipal MemberPrincipal principal,
                                                                  @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate) {
        List<Schedule> schedules = scheduleService.getSchedules(principal, startDate);
        List<ScheduleDto.Response> responses = scheduleMapper.entitiesToResponses(schedules);
        return ResponseEntity.ok(responses);
    }
}
