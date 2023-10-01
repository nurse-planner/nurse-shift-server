package com.nurseshift.shift.nurse;

import com.nurseshift.shift.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    List<Nurse> findAllByMember(Member member);
}
