package com.project.s24521.service;

import com.project.s24521.entity.Role;

import java.util.Optional;

public interface RoleService{

    Optional<Role> findByName(String name);

}
