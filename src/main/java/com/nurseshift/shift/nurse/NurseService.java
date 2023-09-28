package com.nurseshift.shift.nurse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseService {

    private final NurseRepository nurseRepository;

    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public List<Nurse> getNurses() {
        return nurseRepository.findAll();
    }

    public Nurse getNurse(String id) {
        return nurseRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
