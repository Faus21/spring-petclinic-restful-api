package com.project.s24521.controller;

import com.project.s24521.DTOs.AddingAppointmentDto;
import com.project.s24521.DTOs.DoctorDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.repository.DoctorRepository;
import com.project.s24521.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = {"authorization"})
@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/doctor/me")
    public ResponseEntity<?> getDoctorByEmail() throws ObjectNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(doctorService.findDoctorByEmail(authentication.getName()));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> makeDoctor(@RequestBody DoctorDto doctorDto) throws ObjectNotFoundException, ObjectBadRequestException {

        return ResponseEntity.ok(doctorService.createDoctor(doctorDto));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) throws ObjectNotFoundException, ObjectBadRequestException {
        return ResponseEntity.ok(doctorService.deleteDoctor(id));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getDoctors(@RequestParam(defaultValue = "0") final Integer pageNumber) {

        return ResponseEntity.ok(doctorService.findDoctors(pageNumber));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable Integer id) {

        return ResponseEntity.ok(doctorService.getDoctorDataById(id));
    }
}