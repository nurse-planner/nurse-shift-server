package com.nurseshift.shift.nurse;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.schedule.Schedule;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nurse_id")
    private Long id;
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    @Column(name = "is_pregnant")
    private Boolean isPregnant = false;
    @Column(name = "role", nullable = false)
    private Integer role;
    private Integer dutyKeep;
    private String preceptorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    public Nurse(NurseDto.Post post) {
        this.name = post.getName();
        this.isPregnant = post.getIsPregnant();
        this.role = post.getRole();
        this.dutyKeep = post.getDutyKeep();
        this.preceptorId = post.getPreceptorId();
    }

    public Nurse(NurseDto.Patch patch) {
        this.id = patch.getId();
        this.name = patch.getName();
        this.isPregnant = patch.getIsPregnant();
        this.role = patch.getRole();
        this.dutyKeep = patch.getDutyKeep();
        this.preceptorId = patch.getPreceptorId();
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        if (schedule.getNurse() != this) {
            schedule.setNurse(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getNurses().contains(this)) {
            member.getNurses().add(this);
        }
    }
}
