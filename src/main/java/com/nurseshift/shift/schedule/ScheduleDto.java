package com.nurseshift.shift.schedule;

import com.nurseshift.shift.nurse.Nurse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleDto {

    public static class BaseResponse{
        private final LocalDate startDate;
        private final LocalDate endDate;

        public BaseResponse(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String name;
        private final List<String> duties;

        public Response(Nurse nurse, List<Schedule> schedules) {
            this.id = nurse.getId();
            this.name = nurse.getName();
            this.duties = schedules.stream().map(Schedule::getShiftType).collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Post {
        private LocalDate startDate;
        private Integer maxNight;
        private Integer sleepingOff;
        private Integer maxNurse;
        private Integer minNurse;
        private Integer timeout;
        private String[] patterns;
    }
}
