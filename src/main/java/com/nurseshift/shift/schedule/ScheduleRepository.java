package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.nurse.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByMemberAndNurseAndDateBetween(Member member, Nurse nurse, LocalDate start, LocalDate end);

    @Query("select distinct function('month', s.date) from Schedule s")
    List<Integer> findDistinctMonths();

}
