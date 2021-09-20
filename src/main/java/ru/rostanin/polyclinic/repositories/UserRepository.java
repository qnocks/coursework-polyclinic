package ru.rostanin.polyclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rostanin.polyclinic.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
