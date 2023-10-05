package com.nurseshift.shift.nurse.off;

import com.nurseshift.shift.nurse.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffRepository extends JpaRepository<Off, Long> {

    void deleteAllByNurse(Nurse nurse);
}
