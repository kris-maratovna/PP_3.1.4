package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
    private final RolesRepository rolesRepository;

    @Autowired
    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @Override
    public Role getRoleByName(String nameRole) {
        return rolesRepository.findByName(nameRole).orElseThrow();
    }
}
