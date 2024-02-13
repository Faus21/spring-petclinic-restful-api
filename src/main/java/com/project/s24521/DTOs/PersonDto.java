package com.project.s24521.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class PersonDto {

    private String name;

    private String surname;

    private String email;

    private String password;
}
