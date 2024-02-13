package com.project.s24521.service;

import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Person;
import com.project.s24521.entity.PersonPet;
import com.project.s24521.entity.Pet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonPetService {

    List<PersonPet> findByIdPet(Integer idPet);

    List<PersonPet> findPetsByEmail(String email);

    PersonPet findByPersonAndPet(String email, String petName) throws ObjectNotFoundException;
    ResponseEntity<?> addPersonPet(Pet pet, Person person);
}
