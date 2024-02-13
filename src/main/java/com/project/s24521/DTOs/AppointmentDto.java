package com.project.s24521.DTOs;

import com.project.s24521.entity.Doctor;
import com.project.s24521.entity.PersonPet;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class AppointmentDto {

    private Integer idAppointment;

    private String doctorEmail;

    private Timestamp appointmentDate;

    private String petName;

    private String personEmail;

    private String result;
}
