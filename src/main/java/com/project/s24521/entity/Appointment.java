package com.project.s24521.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAppointment;

    @ManyToOne()
    @JoinColumn(
            name = "id_doctor",
            referencedColumnName = "idDoctor"
    )
    @NonNull
    private Doctor doctor;

    @Column(name = "appointment_date")
    @NonNull
    private Date appointmentDate;

    @ManyToOne()
    @JoinColumn(
            name = "id_person_pet",
            referencedColumnName = "idPersonPet"
    )
    @NonNull
    private PersonPet personPet;

    @Column(name = "result")
    private String result;
}
