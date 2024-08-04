package ru.kata.rest.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.rest.app.model.Role;
import ru.kata.rest.app.repositories.RolesRepository;

import java.util.List;


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

    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }
}
