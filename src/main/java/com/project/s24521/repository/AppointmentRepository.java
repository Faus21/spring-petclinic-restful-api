package com.project.s24521.repository;

import com.project.s24521.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("FROM Appointment a WHERE a.personPet.person.email = :email ORDER BY a.appointmentDate")
    List<Appointment> findAppointmentByPerson(@Param("email") String email);

    @Query("FROM Appointment a WHERE a.doctor.person.email = :email ORDER BY a.appointmentDate")
    List<Appointment> findAppointmentByDoctor(@Param("email") String email);
}
