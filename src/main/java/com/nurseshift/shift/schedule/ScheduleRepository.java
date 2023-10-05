package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByMemberAndIdxAndDate(Member member, Long idx, LocalDate date);
    List<Schedule> findAllByMemberAndDate(Member member, LocalDate date);

}
