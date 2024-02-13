package com.project.s24521.controller;

import com.project.s24521.DTOs.JwtCheckTokenDto;
import com.project.s24521.DTOs.JwtResponseDto;
import com.project.s24521.DTOs.LoginDto;
import com.project.s24521.DTOs.RegistrationDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto){
        return authService.createAuthToken(loginDto);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto) throws ObjectBadRequestException {
        return authService.register(registrationDto);
    }

}
