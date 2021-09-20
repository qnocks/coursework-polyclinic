package ru.rostanin.polyclinic.domain;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    private boolean active;

    @NonNull
    private String roles;

    @NonNull
    private String authorities;

    public User(@NonNull String username, @NonNull String password, @NonNull String roles, @NonNull String authorities) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
    }

    public List<String> getRoles() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getAuthorities() {
        if (authorities.length() > 0) {
            return Arrays.asList(authorities.split(","));
        }
        return new ArrayList<>();
    }
}
