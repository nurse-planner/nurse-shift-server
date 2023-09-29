package com.nurseshift.shift.nurse;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseService {

    private final NurseRepository nurseRepository;

    @Transactional
    public Nurse createNurse(Nurse nurse) {
        boolean isExist = nurseRepository.existsById(nurse.getId());
        if (!isExist) {
            return nurseRepository.save(nurse);
        }

        throw new CustomException(ExceptionCode.ID_DUPLICATE);
    }

    @Transactional(readOnly = true)
    public List<Nurse> getNurses() {
        return nurseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Nurse getNurse(String id) {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NURSE_NOT_FOUND));
    }
}
