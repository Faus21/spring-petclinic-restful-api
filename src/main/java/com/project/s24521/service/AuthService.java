package com.project.s24521.service;

import com.project.s24521.DTOs.*;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.WebAppException;
import com.project.s24521.entity.Person;
import com.project.s24521.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonServiceImpl personService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> createAuthToken(LoginDto loginDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(
                    new WebAppException(HttpStatus.UNAUTHORIZED.value(), "Login or password is incorrect!"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = personService.loadUserByUsername(loginDto.getEmail());
        String token = jwtTokenUtils.generateToken(userDetails);
        List<String> roles = jwtTokenUtils.getRoles(token);
        PersonInfoDto personInfoDto = personService.getPersonDataByEmail(loginDto.getEmail());
        return ResponseEntity.ok(new JwtResponseDto(token, personInfoDto.getIdPerson(), roles));
    }

    public ResponseEntity<?> register(RegistrationDto registrationDto) throws ObjectBadRequestException {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())){
            throw new ObjectBadRequestException("Passwords are not the same");
        }

        if (personService.findByEmail(registrationDto.getEmail()).isPresent()){
            throw new ObjectBadRequestException("User is already exists");
        }

        Person person = personService.createNewPerson(registrationDto);


        return ResponseEntity.ok(modelMapper.map(person, PersonDto.class));
    }
}
