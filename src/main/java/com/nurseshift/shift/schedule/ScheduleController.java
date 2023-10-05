package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.authentication.MemberPrincipal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    private final WebClient webClient;


    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper, WebClient.Builder builder) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

//    @PostMapping
//    public Mono<String> create(@RequestBody String data) {
//        return webClient.post()
//                .uri("/async-endpoint")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(data)
//                .retrieve()
//                .bodyToMono(String.class)
//                .doOnSuccess(response -> {
//                    // TODO: DB에 데이터 저장 로직 구현
//                    System.out.println("DB에 데이터 저장: " + response);
//                });
//    }

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
    public ResponseEntity<List<ScheduleDto.BaseResponse>> getSchedule(@AuthenticationPrincipal MemberPrincipal principal) {
        List<ScheduleDto.BaseResponse> dates = scheduleService.getMonths(principal);
        return ResponseEntity.ok(dates);
    }
}
