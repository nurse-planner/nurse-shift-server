package com.nurseshift.shift.nurse;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseService {

    private final NurseRepository nurseRepository;

    public Nurse createNurse(Nurse nurse) {
        boolean isExist = nurseRepository.existsById(nurse.getId());
        if (!isExist) {
            return nurseRepository.save(nurse);
        }

        throw new CustomException(ExceptionCode.ID_DUPLICATE);
    }

    public List<Nurse> getNurses() {
        return nurseRepository.findAll();
    }

    public Nurse getNurse(String id) {
        return nurseRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
