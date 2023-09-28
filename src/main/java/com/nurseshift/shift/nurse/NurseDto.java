package com.nurseshift.shift.nurse;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    public static class Response {
        private String id;
        private String name;
        private Boolean isPregnant;
        private Integer role;
        private Integer dutyKeep;

        public Response(Nurse nurse) {
            this.id = nurse.getId();
            this.name = nurse.getName();
            this.isPregnant = nurse.getIsPregnant();
            this.role = nurse.getRole();
            this.dutyKeep = nurse.getDutyKeep();
        }
    }
}
