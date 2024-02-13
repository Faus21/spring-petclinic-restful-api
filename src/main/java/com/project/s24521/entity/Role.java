package com.project.s24521.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;

    @Column(name = "name")
    @NonNull
    private String name;

    @ManyToMany
    @JoinTable(
            name="person_role",
            joinColumns=@JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_person")
    )
    private List<Person> personSet;
}
