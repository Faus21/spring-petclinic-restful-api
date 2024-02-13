package com.project.s24521.service;

import com.project.s24521.DTOs.PersonInfoDto;
import com.project.s24521.DTOs.RegistrationDto;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Person;
import com.project.s24521.entity.Role;
import com.project.s24521.repository.PersonRepository;
import com.project.s24521.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements UserDetailsService, PersonService {

    private final PersonRepository personRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public Optional<Person> findByEmail(String email){
        return personRepository.findPersonByEmail(email);
    }

    public Optional<Person> findById(Integer id){
        return  personRepository.findById(id);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return new User(person.getEmail(),
                person.getPassword(),
                person.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    public Person createNewPerson(RegistrationDto registrationDto){
        Person person = modelMapper.map(registrationDto, Person.class);
        person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        person.setRoles(Set.of(roleService.findByName("Client").get()));
        personRepository.save(person);
        return person;
    }

    public ResponseEntity<?> addRoles(String role, String email) throws ObjectNotFoundException {
        Person person = findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User is not found"));
        Set<Role> roleSet = person.getRoles();
        roleSet.add(roleService.findByName(role).orElseThrow(() -> new ObjectNotFoundException("Role is not found")));
        person.setRoles(roleSet);
        personRepository.save(person);
        return ResponseEntity.ok("Role is added!");
    }

    @Override
    public void deleteRole(String email, String role) throws ObjectNotFoundException {
        Person person = findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Person is not found!"));
        Set<Role> roles = person.getRoles();
        roles = roles.stream().filter((el) -> !el.getName().equals(role)).collect(Collectors.toSet());

        person.setRoles(roles);
        personRepository.save(person);
    }

    public PersonInfoDto getPersonDataByEmail(String email){
        Person person = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
        PersonInfoDto personInfoDto = modelMapper.map(person, PersonInfoDto.class);
        return personInfoDto;
    }

    @Override
    public Page<PersonInfoDto> getAllPersonsInfo(Integer pageNumber) {
        List<Person> personList = personRepository.findAll(Sort.by("idPerson"));
        List<PersonInfoDto> personInfoDtos = new ArrayList<>();

        for (Person p : personList){
            PersonInfoDto personInfoDto = new PersonInfoDto();
            personInfoDto.setIdPerson(p.getIdPerson());
            personInfoDto.setName(p.getName());
            personInfoDto.setSurname(p.getSurname());
            personInfoDto.setEmail(p.getEmail());

            personInfoDtos.add(personInfoDto);
        }

        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), personInfoDtos.size());
        final Page<PersonInfoDto> page = new PageImpl<>(personInfoDtos.subList(start, end), pageable, personInfoDtos.size());

        return page;

    }

    public PersonInfoDto getPersonDataById(Integer id){
        Person person = findById(id).orElseThrow(() -> new UsernameNotFoundException(id + "not found"));

        PersonInfoDto personInfoDto = new PersonInfoDto();
        personInfoDto.setIdPerson(person.getIdPerson());
        personInfoDto.setEmail(person.getEmail());
        personInfoDto.setName(person.getName());
        personInfoDto.setSurname(person.getSurname());

        return personInfoDto;
    }
}
