package com.project.s24521.controller;

import com.project.s24521.DTOs.AddingAppointmentDto;
import com.project.s24521.DTOs.AppointmentDto;
import com.project.s24521.DTOs.PersonAppointmentDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/person")
    public ResponseEntity<?> getPersonAppointments(@RequestParam(defaultValue = "0") final Integer pageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<PersonAppointmentDto> appointmentByPerson = appointmentService.findAppointmentByPerson(authentication.getName(), pageNumber);
        return ResponseEntity.ok(appointmentByPerson);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAppointments(@RequestParam(defaultValue = "0") final Integer pageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<PersonAppointmentDto> appointmentByPerson = appointmentService.findAppointments(pageNumber);
        return ResponseEntity.ok(appointmentByPerson);
    }

    @GetMapping("/doctor")
    public ResponseEntity<?> getDoctorAppointments(@RequestParam(defaultValue = "0") final Integer pageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Doctor"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Page<PersonAppointmentDto> appointmentByPerson = appointmentService.findAppointmentByDoctor(authentication.getName(), pageNumber);
        return ResponseEntity.ok(appointmentByPerson);
    }

    @PostMapping("/doctor")
    public ResponseEntity<?> addAppointment(@RequestBody AddingAppointmentDto appointmentDto) throws ObjectNotFoundException {
        return ResponseEntity.ok(appointmentService.addAppointment(appointmentDto));
    }

    @PutMapping("/doctor")
    public ResponseEntity<?> updateAppointment(@RequestBody AppointmentDto appointmentDto) throws ObjectNotFoundException, ObjectBadRequestException {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentDto));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Integer id) throws ObjectNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Doctor"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(appointmentService.findByIdDto(id));
    }
}
