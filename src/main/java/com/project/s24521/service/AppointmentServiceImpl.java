package com.project.s24521.service;

import com.project.s24521.DTOs.AddingAppointmentDto;
import com.project.s24521.DTOs.AppointmentDto;
import com.project.s24521.DTOs.PersonAppointmentDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Appointment;
import com.project.s24521.entity.Doctor;
import com.project.s24521.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PersonPetService personPetService;
    private final ModelMapper modelMapper;
    @Override
    public Appointment findById(Integer id) throws ObjectNotFoundException {
        return appointmentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found!"));
    }

    @Override
    public AppointmentDto findByIdDto(Integer id) throws ObjectNotFoundException {
        Appointment appointment = findById(id);
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentDate(new Timestamp(appointment.getAppointmentDate().getTime()));
        appointmentDto.setIdAppointment(appointment.getIdAppointment());
        appointmentDto.setPetName(appointment.getPersonPet().getPet().getPetName());
        appointmentDto.setPersonEmail(appointment.getPersonPet().getPerson().getEmail());
        appointmentDto.setDoctorEmail(appointment.getDoctor().getPerson().getEmail());
        appointmentDto.setResult(appointment.getResult());
        return appointmentDto;
    }

    @Override
    public Page<PersonAppointmentDto> findAppointmentByPerson(String email, Integer pageNumber) {

        List<Appointment> appointments = appointmentRepository.findAppointmentByPerson(email);
        List<PersonAppointmentDto> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments){
            PersonAppointmentDto appointmentDto = new PersonAppointmentDto();
            appointmentDto.setIdAppointment(appointment.getIdAppointment());
            appointmentDto.setIdPerson(appointment.getPersonPet().getPerson().getIdPerson());
            appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
            appointmentDto.setResult(appointment.getResult());
            appointmentDto.setIdDoctor(appointment.getDoctor().getIdDoctor());
            appointmentDto.setIdPet(appointment.getPersonPet().getPet().getIdPet());
            appointmentDto.setDoctorName(appointment.getDoctor().getPerson().getName() + " " + appointment.getDoctor().getPerson().getSurname());
            appointmentDto.setPetName(appointment.getPersonPet().getPet().getPetName());
            appointmentDto.setPersonName(appointment.getPersonPet().getPerson().getName() + " "
                    + appointment.getPersonPet().getPerson().getSurname());

            appointmentDtos.add(appointmentDto);
        }

        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), appointmentDtos.size());
        final Page<PersonAppointmentDto> page = new PageImpl<>(appointmentDtos.subList(start, end), pageable, appointmentDtos.size());

        return page;
    }

    public ResponseEntity<?> addAppointment(AddingAppointmentDto appointmentDto) throws ObjectNotFoundException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getDate());
        appointment.setDoctor(modelMapper.map(doctorService.findDoctorByEmail(appointmentDto.getDoctorEmail()), Doctor.class));
        appointment.setPersonPet(personPetService.findByPersonAndPet(appointmentDto.getPersonEmail(), appointmentDto.getPetName()));

        appointmentRepository.save(appointment);
        return ResponseEntity.ok("OK!");
    }

    @Override
    public ResponseEntity<?> updateAppointment(AppointmentDto appointmentDto) throws ObjectNotFoundException, ObjectBadRequestException {
        Appointment appointment = findById(appointmentDto.getIdAppointment());

        if (Objects.isNull(appointmentDto.getAppointmentDate())) {
            throw new ObjectBadRequestException("Bad request");
        }

        if (appointmentDto.getAppointmentDate().compareTo(new Date())<0) {
            throw new ObjectBadRequestException("Bad request");
        }

        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setResult(appointmentDto.getResult());
        appointment.setDoctor(modelMapper.map(doctorService.findDoctorByEmail(appointmentDto.getDoctorEmail()), Doctor.class));
        appointment.setPersonPet(personPetService.findByPersonAndPet(appointmentDto.getPersonEmail(), appointmentDto.getPetName()));

        appointmentRepository.save(appointment);
        return ResponseEntity.ok("OK!");
    }


    @Override
    public Page<PersonAppointmentDto> findAppointmentByDoctor(String email, Integer pageNumber) {

        List<Appointment> appointments = appointmentRepository.findAppointmentByDoctor(email);
        List<PersonAppointmentDto> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments){
            PersonAppointmentDto appointmentDto = new PersonAppointmentDto();
            appointmentDto.setIdAppointment(appointment.getIdAppointment());
            appointmentDto.setIdPerson(appointment.getPersonPet().getPerson().getIdPerson());
            appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
            appointmentDto.setResult(appointment.getResult());
            appointmentDto.setIdDoctor(appointment.getDoctor().getIdDoctor());
            appointmentDto.setIdPet(appointment.getPersonPet().getPet().getIdPet());
            appointmentDto.setDoctorName(appointment.getDoctor().getPerson().getName() + " " + appointment.getDoctor().getPerson().getSurname());
            appointmentDto.setPetName(appointment.getPersonPet().getPet().getPetName());
            appointmentDto.setPersonName(appointment.getPersonPet().getPerson().getName() + " "
                    + appointment.getPersonPet().getPerson().getSurname());

            appointmentDtos.add(appointmentDto);
        }

        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), appointmentDtos.size());
        final Page<PersonAppointmentDto> page = new PageImpl<>(appointmentDtos.subList(start, end), pageable, appointmentDtos.size());

        return page;
    }

    @Override
    public Page<PersonAppointmentDto> findAppointments(Integer pageNumber) {
        List<Appointment> appointments = appointmentRepository.findAll(Sort.by("appointmentDate"));
        List<PersonAppointmentDto> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments){
            PersonAppointmentDto appointmentDto = new PersonAppointmentDto();
            appointmentDto.setIdAppointment(appointment.getIdAppointment());
            appointmentDto.setIdPerson(appointment.getPersonPet().getPerson().getIdPerson());
            appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
            appointmentDto.setResult(appointment.getResult());
            appointmentDto.setIdDoctor(appointment.getDoctor().getIdDoctor());
            appointmentDto.setIdPet(appointment.getPersonPet().getPet().getIdPet());
            appointmentDto.setDoctorName(appointment.getDoctor().getPerson().getName() + " " + appointment.getDoctor().getPerson().getSurname());
            appointmentDto.setPetName(appointment.getPersonPet().getPet().getPetName());
            appointmentDto.setPersonName(appointment.getPersonPet().getPerson().getName() + " "
                    + appointment.getPersonPet().getPerson().getSurname());

            appointmentDtos.add(appointmentDto);
        }

        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), appointmentDtos.size());
        final Page<PersonAppointmentDto> page = new PageImpl<>(appointmentDtos.subList(start, end), pageable, appointmentDtos.size());

        return page;
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
}
