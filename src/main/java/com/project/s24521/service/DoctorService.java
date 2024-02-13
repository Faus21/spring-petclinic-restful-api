package com.project.s24521.service;

import com.project.s24521.DTOs.DoctorDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface DoctorService {

    Optional<Doctor> findById(Integer id);

    DoctorDto findDoctorByEmail(String email) throws ObjectNotFoundException;

    Page<DoctorDto> findDoctors(Integer pageNumber);

    DoctorDto getDoctorDataById(Integer id);

    ResponseEntity<?> deleteDoctor(Integer id) throws ObjectNotFoundException, ObjectBadRequestException;

    ResponseEntity<?> createDoctor(DoctorDto doctorDto) throws ObjectNotFoundException, ObjectBadRequestException;
}
