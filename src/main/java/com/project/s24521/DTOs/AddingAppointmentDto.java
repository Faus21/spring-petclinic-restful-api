package com.project.s24521.DTOs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddingAppointmentDto {

    private String personEmail;

    private String doctorEmail;

    private String petName;

    private Timestamp date;

}
