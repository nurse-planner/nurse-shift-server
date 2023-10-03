package com.nurseshift.shift.schedule;

import com.nurseshift.shift.nurse.Nurse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "shift_type", nullable = false)
    private Integer shiftType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    public Schedule(Long id, LocalDate date, Integer shiftType) {
        this.id = id;
        this.date = date;
        this.shiftType = shiftType;
    }
}
