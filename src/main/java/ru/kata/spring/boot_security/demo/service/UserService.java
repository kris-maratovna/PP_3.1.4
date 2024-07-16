package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    List<User> showAll();
    Optional<User> findById(Long id);
    void edit(User user);
    void delete(Long id);
    User getByUsername(String username);
}
