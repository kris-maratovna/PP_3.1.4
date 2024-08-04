package ru.kata.rest.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.rest.app.model.Role;

import java.util.Optional;


@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (String roleName);

}
