package com.nurseshift.shift.nurse;

import com.nurseshift.shift.common.exception.CustomException;
import com.nurseshift.shift.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Nurse updateNurse(Nurse nurse) {
        Nurse findNurse = verfiyNurse(nurse.getId());

        Optional.ofNullable(nurse.getRole())
                .ifPresent(findNurse::setRole);
        Optional.ofNullable(nurse.getName())
                .ifPresent(findNurse::setName);
        Optional.ofNullable(nurse.getIsPregnant())
                .ifPresent(findNurse::setIsPregnant);
        Optional.ofNullable(nurse.getPreceptorId())
                .ifPresent(findNurse::setPreceptorId);
        Optional.ofNullable(nurse.getDutyKeep())
                .ifPresent(findNurse::setDutyKeep);

        return nurseRepository.save(findNurse);
    }

    @Transactional(readOnly = true)
    public List<Nurse> getNurses() {
        return nurseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Nurse getNurse(String id) {
        return verfiyNurse(id);
    }

    @Transactional
    public void deleteNurse(String id) {
        boolean isExist = nurseRepository.existsById(id);
        if (!isExist) {
            throw new CustomException(ExceptionCode.NURSE_NOT_FOUND);
        }

        nurseRepository.deleteById(id);
    }

    private Nurse verfiyNurse(String nurse) {
        return nurseRepository.findById(nurse)
                .orElseThrow(() -> new CustomException(ExceptionCode.NURSE_NOT_FOUND));
    }
}
