package ru.kata.rest.app.service;

import ru.kata.rest.app.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String nameRole);
    public List<Role> findAll();
}
