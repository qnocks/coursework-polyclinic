package ru.rostanin.polyclinic.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.domain.User;
import ru.rostanin.polyclinic.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DbAuthInit implements CommandLineRunner {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private long id = 0L;

        @Autowired
        public DbAuthInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {
            List<User> existingUsers = userRepository.findAll();
            long count = existingUsers.stream()
                    .filter(u -> u.getUsername().equals("admin") || u.getUsername().equals("user"))
                    .count();
            if (count == 2) {
                log.info("ADMIN and USER are already in the database");
                return;
            }
            User user = new User("user", passwordEncoder.encode("user"), "USER",
                    "READ_DOCTORS,READ_PATIENTS,READ_APPOINTMENTS,WRITE_APPOINTMENT");

            User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN",
                    "READ_DOCTORS,WRITE_DOCTOR,EDIT_DOCTOR," +
                             "READ_PATIENTS,WRITE_PATIENT,EDIT_PATIENT," +
                             "READ_APPOINTMENTS,WRITE_APPOINTMENT,EDIT_APPOINTMENT");

            List<User> users = Arrays.asList(user, admin);
            userRepository.saveAll(users);
            log.info("ADMIN and USER have SAVED to the database");
        }
}
