package com.project.s24521.repository;

import com.project.s24521.entity.Appointment;
import com.project.s24521.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("FROM Doctor d WHERE d.person.email = :email")
    Optional<Doctor> findDoctorByEmail(@Param("email") String email);
}
