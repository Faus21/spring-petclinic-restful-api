package com.project.s24521.DTOs;

import lombok.Data;

@Data
public class RegistrationDto {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
