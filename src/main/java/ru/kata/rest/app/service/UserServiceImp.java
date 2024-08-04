package ru.kata.rest.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.rest.app.model.Role;
import ru.kata.rest.app.model.User;
import ru.kata.rest.app.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public List<User> showAll() {
        return usersRepository.findAll();
    }
    @Override
    public User findById(Long id) {
        User user = null;
        Optional<User> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }
    @Override
    public void edit(User user) {
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        var currentPassword = usersRepository.findById(user.getId()).get().getPassword();
        if (password.equals(currentPassword)) {
            usersRepository.save(user);
        } else {
            user.setPassword(encode);
            usersRepository.save(user);
        }
    }
    @Override
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }
    @Override
    public User getByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }

    @Override
    public boolean isAdmin(String username) {
        User user = usersRepository.findByUsername(username).orElseThrow();
        return user.getRole().stream()
                .map(Role::getName)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
