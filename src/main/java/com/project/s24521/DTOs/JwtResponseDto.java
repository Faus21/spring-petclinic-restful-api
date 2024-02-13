package com.project.s24521.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String token;

    private Integer idPerson;

    private List<String> roles;
}
