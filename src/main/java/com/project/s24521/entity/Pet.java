package com.project.s24521.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "pet")
@NoArgsConstructor
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPet;

    @Column(name="petname")
    @NonNull
    private String petName;

    @Column(name="age")
    @NonNull
    private Integer age;

    @Column(name="type")
    @NonNull
    private String type;

    @OneToMany(mappedBy="pet")
    @NonNull
    private List<PersonPet> personPetList;
}
