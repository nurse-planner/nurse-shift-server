package com.nurseshift.shift.nurse.off;

import com.nurseshift.shift.nurse.Nurse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Off {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_id")
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Boolean type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    public Off(LocalDate date, Boolean type) {
        this.date = date;
        this.type = type;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
        if (!nurse.getOffs().contains(this)) {
            nurse.getOffs().add(this);
        }
    }
}
