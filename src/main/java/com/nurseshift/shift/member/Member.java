package com.nurseshift.shift.member;

import com.nurseshift.shift.nurse.Nurse;
import com.nurseshift.shift.schedule.Schedule;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, length = 45, unique = true)
    private String email;
    @Column(nullable = false, length = 150)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Nurse> nurses = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        if (schedule.getMember() != this) {
            schedule.setMember(this);
        }
    }

    public void addNurse(Nurse nurse) {
        this.nurses.add(nurse);
        if (nurse.getMember() != this) {
            nurse.setMember(this);
        }
    }
}
