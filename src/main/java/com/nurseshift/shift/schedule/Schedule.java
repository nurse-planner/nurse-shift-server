package com.nurseshift.shift.schedule;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.nurse.Nurse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "shift_type", nullable = false)
    private String shiftType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
        if (!nurse.getSchedules().contains(this)) {
            nurse.getSchedules().add(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getSchedules().contains(this)) {
            member.getSchedules().add(this);
        }
    }
}
