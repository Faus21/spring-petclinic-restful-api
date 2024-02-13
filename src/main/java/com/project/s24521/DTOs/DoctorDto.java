package com.project.s24521.DTOs;

import lombok.Data;


@Data
public class DoctorDto {

    private Integer idDoctor;

    private String name;

    private String surname;

    private String email;

    private String qualification;
}
