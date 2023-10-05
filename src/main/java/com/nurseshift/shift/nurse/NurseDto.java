package com.nurseshift.shift.nurse;

import com.nurseshift.shift.nurse.off.Off;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class NurseDto {

    @AllArgsConstructor
    @Getter
    public static class Post {
        private String name;
        private Boolean isPregnant;
        private Integer role;
        private Integer dutyKeep;
        private String preceptorId;
        private List<LocalDate> offs;
        private List<LocalDate> rests;
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
        private List<LocalDate> offs;
        private List<LocalDate> rests;
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
        private final List<LocalDate> offs;
        private final List<LocalDate> rests;

        public Response(Nurse nurse) {
            this.id = nurse.getId();
            this.name = nurse.getName();
            this.isPregnant = nurse.getIsPregnant();
            this.role = nurse.getRole();
            this.dutyKeep = nurse.getDutyKeep();
            this.preceptorId = nurse.getPreceptorId();
            this.offs = nurse.getOffs().stream().filter(Off::getType).map(Off::getDate).collect(Collectors.toList());
            this.rests = nurse.getOffs().stream().filter(off -> !off.getType()).map(Off::getDate).collect(Collectors.toList());
        }
    }
}
