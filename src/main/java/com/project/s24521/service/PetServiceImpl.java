package com.project.s24521.service;

import com.project.s24521.DTOs.AddingPetDto;
import com.project.s24521.DTOs.DoctorDto;
import com.project.s24521.DTOs.PetDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.entity.PersonPet;
import com.project.s24521.entity.Pet;
import com.project.s24521.repository.PersonPetRepository;
import com.project.s24521.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private final PersonPetService personPetService;
    private final PersonService personService;


    @Override
    public PetDto findPersonPetById(Integer id, String email) throws ObjectNotFoundException, ObjectBadRequestException {
        if (personPetService
                .findByIdPet(id).stream()
                .filter(e -> e.getPerson().getEmail().equals(email)
                ).toList().isEmpty()) {
            throw new ObjectBadRequestException("Bad request!");
        }

        Pet pet = petRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pet not found!"));
        PetDto petDto = modelMapper.map(pet, PetDto.class);
        return petDto;
    }

    @Override
    public PetDto findPersonPetById(Integer id) throws ObjectNotFoundException, ObjectBadRequestException {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pet not found!"));
        PetDto petDto = modelMapper.map(pet, PetDto.class);
        return petDto;
    }

    @Override
    public List<PetDto> findPetsByEmail(String email) throws ObjectNotFoundException {
        List<PetDto> petDtos = new ArrayList<>();

        List<PersonPet> petList = personPetService.findPetsByEmail(email.trim());

        for (PersonPet p :
                petList) {
            petDtos.add(modelMapper.map(p.getPet(), PetDto.class));
        }

        return petDtos;
    }

    @Override
    public Page<PetDto> findAllPets(Integer pageNumber) {
        List<Pet> pets = petRepository.findAll(Sort.by("idPet"));
        List<PetDto> petDtos = new ArrayList<>();

        for(Pet p : pets){
            petDtos.add(modelMapper.map(p, PetDto.class));
        }

        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), petDtos.size());
        final Page<PetDto> page = new PageImpl<>(petDtos.subList(start, end), pageable, petDtos.size());

        return page;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addPet(AddingPetDto petDto) throws ObjectNotFoundException {

        Pet pet = new Pet();
        pet.setPetName(petDto.getPetName());
        pet.setAge(petDto.getAge());
        pet.setType(petDto.getType());

        petRepository.save(pet);

        personPetService.addPersonPet(pet, personService.findByEmail(petDto.getClientEmail()).orElseThrow(() ->
                new ObjectNotFoundException("Not found!")));

        return ResponseEntity.ok("Ok!");
    }

}
