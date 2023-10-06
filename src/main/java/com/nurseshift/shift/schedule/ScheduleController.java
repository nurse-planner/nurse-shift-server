package com.nurseshift.shift.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurseshift.shift.member.authentication.MemberPrincipal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
        this.webClient = builder.baseUrl("http://uzrylghpmb.us19.qoddiapp.com").build();
    }

    @PostMapping
    public Mono<String> create(@AuthenticationPrincipal MemberPrincipal principal, @RequestBody ScheduleDto.Post post) {
        return webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(scheduleService.getRequestData(principal, post))
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(response -> {
                    response = response.replaceAll("\\\\", "");
                    response = response.substring(1, response.length() - 1);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(
                            DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
                    try {
                        ScheduleDto.Result[] result = objectMapper.readValue(response, ScheduleDto.Result[].class);
                        scheduleService.createSchedule(principal, result, post.getStartDate());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

//    @PostMapping
//    public ResponseEntity<?> postSchedule(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
//        scheduleService.createSchedule(memberPrincipal);
//        return null;
//    }

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
