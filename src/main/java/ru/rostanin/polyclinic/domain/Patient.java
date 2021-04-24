package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class Patient {

    @NotNull(message = "Patient's id can't be null")
    private Long id;

    @NotBlank(message = "Registration number cannot be blank")
    private String registrationNumber;

    @NotBlank(message = "Patient's full name cannot be blank")
    private String fullName;

    @NotNull(message = "This field cannot be null")
    @Past(message = "Birth date must be in the past")
    @Pattern(regexp = "^([0-2][0-9]||3[0-1]).(0[0-9]||1[0-2]).([0-9][0-9])?[0-9][0-9]$",
            message = "Patient's birthdate should match pattern dd.mm.yyyy")
    private String birthdate;

    @NotBlank(message = "This field cannot be blank")
    private String address;

    @NotBlank(message = "This field cannot be blank")
    private String job;

}
