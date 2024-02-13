package com.project.s24521.DTOs;


import lombok.Data;

import java.util.Date;

@Data
public class PersonAppointmentDto {


    private Integer idAppointment;

    private Integer idDoctor;

    private String doctorName;

    private Date appointmentDate;

    private Integer idPerson;

    private Integer idPet;

    private String petName;

    private String personName;

    private String result;

}
