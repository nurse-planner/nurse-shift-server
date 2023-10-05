package com.nurseshift.shift.schedule;

import com.nurseshift.shift.nurse.Nurse;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ScheduleMapper {

    public ScheduleDto.Response entityToResponse(Nurse nurse, List<Schedule> schedule) {
        return new ScheduleDto.Response(nurse, schedule);
    }
}
