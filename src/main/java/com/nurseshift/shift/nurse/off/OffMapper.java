package com.nurseshift.shift.nurse.off;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class OffMapper {

    public List<Off> requestToEntity(List<LocalDate> dates, boolean isOff){
        return dates.stream().map(date -> new Off(date, isOff)).collect(Collectors.toList());
    }
}
