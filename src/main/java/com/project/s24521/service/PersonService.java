package com.project.s24521.service;

import com.project.s24521.DTOs.PersonInfoDto;
import com.project.s24521.DTOs.RegistrationDto;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PersonService {

    Person createNewPerson(RegistrationDto registrationDto);

    Optional<Person> findByEmail(String email);

    Optional<Person> findById(Integer id);

    PersonInfoDto getPersonDataById(Integer id);

    void deleteRole(String email, String role) throws ObjectNotFoundException;

    PersonInfoDto getPersonDataByEmail(String email);

    Page<PersonInfoDto> getAllPersonsInfo(Integer pageNumber);

    ResponseEntity<?> addRoles(String role, String email) throws ObjectNotFoundException;

}
