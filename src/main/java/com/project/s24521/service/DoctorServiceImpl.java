package com.project.s24521.service;

import com.project.s24521.DTOs.DoctorDto;
import com.project.s24521.DTOs.PersonAppointmentDto;
import com.project.s24521.DTOs.exception.ObjectBadRequestException;
import com.project.s24521.DTOs.exception.ObjectNotFoundException;
import com.project.s24521.entity.Doctor;
import com.project.s24521.entity.Person;
import com.project.s24521.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final RoleService roleService;
    private final PersonService personService;
    private final ModelMapper modelMapper;
    @Override
    public Optional<Doctor> findById(Integer id) {
        return doctorRepository.findById(id);
    }

    @Override
    public DoctorDto findDoctorByEmail(String email) throws ObjectNotFoundException {
        Doctor doctor = doctorRepository.findDoctorByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Not found!"));
        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    public Page<DoctorDto> findDoctors(Integer pageNumber) {

        List<Doctor> doctors = doctorRepository.findAll(Sort.by("idDoctor"));
        List<DoctorDto> doctorDtos = new ArrayList<>();

        for (Doctor d : doctors) {
            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setIdDoctor(d.getIdDoctor());
            doctorDto.setName(d.getPerson().getName());
            doctorDto.setSurname(d.getPerson().getSurname());
            doctorDto.setEmail(d.getPerson().getEmail());
            doctorDto.setQualification(d.getQualification());

            doctorDtos.add(doctorDto);
        }
        final Pageable pageable = PageRequest.of(pageNumber, 5);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), doctorDtos.size());
        final Page<DoctorDto> page = new PageImpl<>(doctorDtos.subList(start, end), pageable, doctorDtos.size());

        return page;
    }

    @Override
    public DoctorDto getDoctorDataById(Integer id) {
        Doctor d = findById(id).orElseThrow(() -> new UsernameNotFoundException(id + "not found"));
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setIdDoctor(d.getIdDoctor());
        doctorDto.setName(d.getPerson().getName());
        doctorDto.setSurname(d.getPerson().getSurname());
        doctorDto.setEmail(d.getPerson().getEmail());
        doctorDto.setQualification(d.getQualification());

        return doctorDto;
    }

    @Override
    public ResponseEntity<?> deleteDoctor(Integer id) throws ObjectNotFoundException, ObjectBadRequestException {
        if (doctorRepository.findById(id).isEmpty())
            throw new ObjectBadRequestException("Person is not a doctor!");

        Doctor doctor = doctorRepository.findById(id).get();

        if (personService.findByEmail(doctor.getPerson().getEmail()).isEmpty())
            throw new ObjectNotFoundException("No such person!");

        personService.deleteRole(doctor.getPerson().getEmail(), "Doctor");
        doctorRepository.delete(doctor);
        return ResponseEntity.ok("Ok!");
    }

    @Override
    public ResponseEntity<?> createDoctor(DoctorDto doctorDto) throws ObjectNotFoundException, ObjectBadRequestException {
        if (doctorRepository.findDoctorByEmail(doctorDto.getEmail()).isPresent())
            throw new ObjectBadRequestException("Doctor is already present!");
        if (personService.findByEmail(doctorDto.getEmail()).isEmpty())
            throw new ObjectNotFoundException("No such person!");
        if (Objects.isNull(doctorDto.getQualification()))
            throw new ObjectBadRequestException("Qualification can not be null");

        personService.addRoles("Doctor", doctorDto.getEmail());

        Person person = personService.findByEmail(doctorDto.getEmail()).get();
        Doctor doctor = new Doctor();
        doctor.setPerson(person);
        doctor.setQualification(doctorDto.getQualification());
        doctorRepository.save(doctor);

        return ResponseEntity.ok("Doctor is created!");
    }


}
