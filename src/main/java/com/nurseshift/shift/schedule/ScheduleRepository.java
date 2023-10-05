package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.nurse.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByMemberAndNurseAndDateBetween(Member member, Nurse nurse, LocalDate start, LocalDate end);
    @Query("select function('year', s.date) as year, function('month', s.date) as month from Schedule s group by year, month")
    List<Object[]> findDistinctYearMonths();
    @Query("select count(distinct s.nurse.id) from Schedule s where function('year', s.date) = :year and function('month', s.date) = :month and s.member.id = :memberId")
    Long countDistinctNursesByYearAndMonth(@Param("year") int year, @Param("month") int month, @Param("memberId") Long memberId);
}
