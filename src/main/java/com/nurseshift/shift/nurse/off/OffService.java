package com.nurseshift.shift.nurse.off;

import com.nurseshift.shift.nurse.Nurse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OffService {

    private final OffRepository offRepository;

    @Transactional
    public void createOff(List<Off> offs, Nurse nurse){
        offs.forEach(off -> {
            nurse.addOff(off);
            offRepository.save(off);
        });
    }

    @Transactional
    public void deleteOff(Nurse nurse){
        offRepository.deleteAllByNurse(nurse);
    }
}
