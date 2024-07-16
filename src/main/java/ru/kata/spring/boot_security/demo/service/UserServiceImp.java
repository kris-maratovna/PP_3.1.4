package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void add(User user) {
        usersRepository.save(user);
    }

    @Override
    public List<User> showAll() {
        return usersRepository.findAll();
    }
    @Override
    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);
    }
    @Override
    public void edit(User user) {
        usersRepository.save(user);
    }
    @Override
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }
    @Override
    public User getByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }
}
