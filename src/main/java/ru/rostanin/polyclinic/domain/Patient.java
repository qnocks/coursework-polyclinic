package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    @NotBlank(message = "Registration number cannot be blank")
    // TODO: regex
    private String registrationNumber;

    @Column(name = "full_name")
    @NotBlank(message = "Patient's full name cannot be blank")
    private String fullName;


    @Column(name = "birth_date")
    @NotNull(message = "This field cannot be null")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$",
            message = "Patient's birthdate should match pattern dd.mm.yyyy")
    private String birthDate;

    @Column(name = "address")
    @NotBlank(message = "This field cannot be blank")
    private String address;

    @Column(name = "job")
    @NotBlank(message = "This field cannot be blank")
    private String job;

}
