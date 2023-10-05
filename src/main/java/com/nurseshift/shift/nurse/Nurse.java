package com.nurseshift.shift.nurse;

import com.nurseshift.shift.member.Member;
import com.nurseshift.shift.schedule.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Schedule> schedules;

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
}
