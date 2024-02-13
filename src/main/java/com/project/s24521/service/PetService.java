package com.project.s24521.service;

import com.project.s24521.DTOs.AddingPetDto;
import com.project.s24521.DTOs.PetDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PetService {

    PetDto findPersonPetById(Integer id, String email) throws ObjectNotFoundException, ObjectBadRequestException;
     PetDto findPersonPetById(Integer id) throws ObjectNotFoundException, ObjectBadRequestException;

    List<PetDto> findPetsByEmail(String email)  throws ObjectNotFoundException;


    Page<PetDto> findAllPets(Integer pageNumber);

    ResponseEntity<?> addPet(AddingPetDto petDto) throws ObjectNotFoundException;
}
