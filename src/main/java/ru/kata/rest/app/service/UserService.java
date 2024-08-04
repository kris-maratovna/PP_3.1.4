package ru.kata.rest.app.service;

import ru.kata.rest.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    List<User> showAll();
    User findById(Long id);
    void edit(User user);
    void delete(Long id);
    User getByUsername(String username);
    boolean isAdmin(String username);
}
