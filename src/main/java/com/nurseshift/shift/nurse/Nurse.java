package com.nurseshift.shift.nurse;

import com.nurseshift.shift.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Nurse {

    @Id
    @Column(name = "id", unique = true)
    private String id;
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    @Column(name = "is_pregnant")
    private Boolean isPregnant = false;
    @Column(name = "role", nullable = false)
    private Integer role;
    private Integer dutyKeep;
    private String preceptorId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Nurse(NurseDto.Post post) {
        this.id = post.getId();
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
