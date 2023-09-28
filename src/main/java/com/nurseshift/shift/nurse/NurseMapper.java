package com.nurseshift.shift.nurse;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class NurseMapper {

    public Nurse postToEntity(NurseDto.Post post) {
        return new Nurse(post);
    }

    public NurseDto.Response entityToResponse(Nurse nurse) {
        return new NurseDto.Response(nurse);
    }

    public List<NurseDto.Response> entitiesToResponses(List<Nurse> nurses) {
        return nurses.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
