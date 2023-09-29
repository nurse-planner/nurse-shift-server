package com.nurseshift.shift.nurse;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
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

    public Nurse(NurseDto.Post post) {
        this.id = post.getId();
        this.name = post.getName();
        this.isPregnant = post.getIsPregnant();
        this.role = post.getRole();
        this.dutyKeep = post.getDutyKeep();
        this.preceptorId = post.getPreceptorId();
    }
}
