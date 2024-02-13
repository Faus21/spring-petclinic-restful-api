package com.project.s24521.controller;

import com.project.s24521.service.PersonService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = {"authorization"})
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(personService.getPersonDataByEmail(authentication.getName()));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))
            || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("Doctor"))){
            return ResponseEntity.ok(personService.getPersonDataByEmail(authentication.getName()));
        }

        return ResponseEntity.ok(personService.getPersonDataById(id));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllPersons(@RequestParam(defaultValue = "0") final Integer pageNumber){
        
        return ResponseEntity.ok(personService.getAllPersonsInfo(pageNumber));
    }
}
