package com.nurseshift.shift.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class ScheduleDto {

    @Getter
    public static class Response {

        private final Long id;
        private final String title;
        private final String content;

        public Response(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.content = schedule.getContent();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Post {
        private LocalDate startDate;
        private LocalDate endDate;
        private String name;
        private Integer limit;
        private Integer sleepingOff;
        private Integer maxNurse;
        private Integer minNurse;
        private Integer timeout;
        private String pattern;
    }
}
