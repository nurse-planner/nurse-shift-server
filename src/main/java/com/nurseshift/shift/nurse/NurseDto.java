package com.nurseshift.shift.nurse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NurseDto {

    @AllArgsConstructor
    @Getter
    public static class Post {
        private String name;
        private Boolean isPregnant;
        private Integer role;
        private Integer dutyKeep;
        private String preceptorId;
    }

    @AllArgsConstructor
    @Getter
    public static class Patch {
        private Long id;
        private String name;
        private Boolean isPregnant;
        private Integer role;
        private Integer dutyKeep;
        private String preceptorId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Delete {
        private Long id;
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String name;
        private final Boolean isPregnant;
        private final Integer role;
        private final Integer dutyKeep;
        private final String preceptorId;

        public Response(Nurse nurse) {
            this.id = nurse.getId();
            this.name = nurse.getName();
            this.isPregnant = nurse.getIsPregnant();
            this.role = nurse.getRole();
            this.dutyKeep = nurse.getDutyKeep();
            this.preceptorId = nurse.getPreceptorId();
        }
    }
}
