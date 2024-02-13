package com.project.s24521.DTOs;

import lombok.Data;

@Data
public class AddingPetDto {
    private String clientEmail;
    private String petName;
    private Integer age;

    private String type;
}
