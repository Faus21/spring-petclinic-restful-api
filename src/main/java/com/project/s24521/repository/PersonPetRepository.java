package com.project.s24521.repository;

import com.project.s24521.entity.PersonPet;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonPetRepository extends JpaRepository<PersonPet, Integer> {

    @Query("FROM PersonPet WHERE pet.idPet = :idPet")
    List<PersonPet> findPersonPetByPet(@Param("idPet") Integer idPet);

    @Query("FROM PersonPet WHERE person.email = :email")
    List<PersonPet> findPetsByEmail(@Param("email") String email);

    @Query("FROM PersonPet WHERE person.email = :email AND pet.petName = :petName")
    Optional<PersonPet> findByPersonAndPet(@Param("email") String email, @Param("petName") String petName);
}
