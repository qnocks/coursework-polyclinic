package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    @NotBlank(message = "Registration number cannot be blank")
    private String registrationNumber;

    @Column(name = "full_name")
    @NotBlank(message = "This field cannot be blank")
    @Size(max = 25, message = "Full name's length must be up to 25 characters")
    private String fullName;

    @Column(name = "date")
    @NotBlank(message = "This field cannot be blank")
    @Pattern(regexp = "^([0-2][0-9]||3[0-1]).(0[0-9]||1[0-2]).([0-9][0-9])?[0-9][0-9]$",
            message = "Date of appointment should match pattern dd.mm.yyyy")
    private String date;

    @Column(name = "time")
    @NotBlank(message = "This field cannot be blank")
    private String time;
}
