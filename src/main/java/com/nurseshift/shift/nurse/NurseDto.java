package com.nurseshift.shift.nurse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NurseDto {

    @AllArgsConstructor
    @Getter
    public static class Post {
        private String id;
        private String name;
        private Boolean isPregnant;
        private Integer role;
        private Integer dutyKeep;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Delete {
        private String id;
    }

    @Getter
    public static class Response {
        private final String id;
        private final String name;
        private final Boolean isPregnant;
        private final Integer role;
        private final Integer dutyKeep;

        public Response(Nurse nurse) {
            this.id = nurse.getId();
            this.name = nurse.getName();
            this.isPregnant = nurse.getIsPregnant();
            this.role = nurse.getRole();
            this.dutyKeep = nurse.getDutyKeep();
        }
    }
}
