package com.project.s24521.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name="Doctor")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDoctor;

    @OneToOne()
    @JoinColumn(
            name = "id_person",
            referencedColumnName = "idPerson"
    )
    @NonNull
    private Person person;

    @Column(name="qualification")
    @NonNull
    private String qualification;

    @OneToMany(mappedBy="doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;
}
