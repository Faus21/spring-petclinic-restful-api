package com.project.s24521.service;

import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Person;
import com.project.s24521.entity.PersonPet;
import com.project.s24521.entity.Pet;
import com.project.s24521.repository.PersonPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RequiredArgsConstructor
@Service
@CrossOrigin(origins = "http://localhost:3000")
public class PersonPetServiceImpl implements PersonPetService{

    private final PersonPetRepository personPetRepository;
    @Override
    public List<PersonPet> findByIdPet(Integer idPet) {
        return personPetRepository.findPersonPetByPet(idPet);
    }

    @Override
    public ResponseEntity<?> addPersonPet(Pet pet, Person person){
        PersonPet personPet = new PersonPet();

        personPet.setPet(pet);
        personPet.setPerson(person);

        personPetRepository.save(personPet);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public List<PersonPet> findPetsByEmail(String email) {
        return personPetRepository.findPetsByEmail(email);
    }

    @Override
    public PersonPet findByPersonAndPet(String email, String petName) throws ObjectNotFoundException {
        return personPetRepository.findByPersonAndPet(email, petName).orElseThrow(() -> new ObjectNotFoundException("Not found!"));
    }
}
