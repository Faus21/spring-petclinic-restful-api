package com.project.s24521.controller;

import com.project.s24521.DTOs.AddingAppointmentDto;
import com.project.s24521.DTOs.AddingPetDto;
import com.project.s24521.DTOs.PetDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Pet;
import com.project.s24521.repository.PetRepository;
import com.project.s24521.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PetController{


    private final PetService petService;

    @GetMapping("/person/{id}")
    public ResponseEntity<?> getPet(@PathVariable Integer id) throws ObjectNotFoundException, ObjectBadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(
                new SimpleGrantedAuthority("Doctor"))){
            return ResponseEntity.ok(petService.findPersonPetById(id));
        }
        return ResponseEntity.ok(petService.findPersonPetById(id, authentication.getName()));
    }

    @GetMapping("/doctor/getByPerson")
    public ResponseEntity<?> getPetByPerson(@Param("email") String email)  throws ObjectNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(email) || email.isEmpty()) return ResponseEntity.ok(petService.findPetsByEmail(authentication.getName()));

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Doctor")))
            throw new ObjectNotFoundException("You don't have access to it!");

        return ResponseEntity.ok(petService.findPetsByEmail(email));
    }

    @PostMapping("/doctor")
    public ResponseEntity<?> addPet(@RequestBody AddingPetDto petDto) throws ObjectNotFoundException {
        return petService.addPet(petDto);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getPets(@RequestParam(defaultValue = "0") final Integer pageNumber){

        return ResponseEntity.ok(petService.findAllPets(pageNumber));
    }
}
