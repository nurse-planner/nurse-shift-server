package com.nurseshift.shift.schedule;

import com.nurseshift.shift.nurse.Nurse;
import com.nurseshift.shift.nurse.NurseDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleDto {

    @Getter
    public static class BaseResponse {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final Long nurseCount;
        private final Boolean created = true;

        public BaseResponse(LocalDate startDate, LocalDate endDate, Long nurseCount) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.nurseCount = nurseCount;
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

    @Getter
    public static class PreResponse implements Serializable {
        private final LocalDate startDate;
        private final Integer day;
        private final Integer maxNight;
        private final Integer sleepingOff;
        private final Integer maxNurse;
        private final Integer minNurse;
        private final String[] patterns;
        private final List<NurseDto.PretreatmentResponse> chargeNurses;
        private final List<NurseDto.PretreatmentResponse> actNurses;

        public PreResponse(Post post, Integer day, List<NurseDto.PretreatmentResponse> chargeNurses, List<NurseDto.PretreatmentResponse> actNurses) {
            this.startDate = post.getStartDate();
            this.day = day;
            this.maxNight = post.getMaxNight();
            this.sleepingOff = post.getSleepingOff();
            this.maxNurse = post.getMaxNurse();
            this.minNurse = post.getMinNurse();
            this.patterns = post.getPatterns();
            this.chargeNurses = chargeNurses;
            this.actNurses = actNurses;
        }
    }


    @AllArgsConstructor
    @Getter
    @Setter
    public static class Result {
        Integer id;
        Map<String, String> day;
    }

}
