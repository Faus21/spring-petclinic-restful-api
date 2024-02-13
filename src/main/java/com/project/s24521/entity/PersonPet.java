package com.project.s24521.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "person_pet")
@Getter
@Setter
@NoArgsConstructor
public class PersonPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersonPet;

    @ManyToOne()
    @JoinColumn(
            name = "id_person",
            referencedColumnName = "idPerson"
    )
    @NonNull
    private Person person;

    @ManyToOne()
    @JoinColumn(
            name = "id_pet",
            referencedColumnName = "idPet"
    )
    @NonNull
    private Pet pet;
}
