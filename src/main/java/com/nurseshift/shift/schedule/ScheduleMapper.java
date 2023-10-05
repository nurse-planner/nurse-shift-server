package com.nurseshift.shift.schedule;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ScheduleMapper {

    public ScheduleDto.Response entityToResponse(Schedule schedule) {
        return new ScheduleDto.Response(schedule);
    }

    public List<ScheduleDto.Response> entitiesToResponses(List<Schedule> schedules) {
        return schedules.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
