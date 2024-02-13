package com.project.s24521.service;

import com.project.s24521.DTOs.AddingAppointmentDto;
import com.project.s24521.DTOs.AppointmentDto;
import com.project.s24521.DTOs.PersonAppointmentDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Appointment findById(Integer id) throws ObjectNotFoundException;

    AppointmentDto findByIdDto(Integer id) throws ObjectNotFoundException;

    Page<PersonAppointmentDto> findAppointmentByPerson(String email, Integer page);

    Page<PersonAppointmentDto> findAppointmentByDoctor(String email, Integer page);

    Page<PersonAppointmentDto> findAppointments(Integer pageNumber);


    public ResponseEntity<?> addAppointment(AddingAppointmentDto appointmentDto) throws ObjectNotFoundException;

    ResponseEntity<?> updateAppointment(AppointmentDto appointmentDto) throws ObjectNotFoundException, ObjectBadRequestException;

    List<Appointment> findAll();
}
