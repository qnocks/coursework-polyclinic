package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class Doctor {

    @NotNull(message = "This field can't be null")
    private Long id;

    @NotBlank(message = "This field cannot be blank")
    @Size(max = 25, message = "Full name's length must be up to 25 characters")
    private String fullName;

    @NotBlank(message = "This field cannot be blank")
    private String specialty;

    @NotNull(message = "This field can't be null")
    @Min(value = 1, message = "The office number cannot be less then 1")
    private int officeNumber;

    @NotBlank(message = "This field cannot be blank")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]",
            message = "Doctor's schedule should match pattern HH:MM-HH:MM 24 hours")
    private String schedule;

}
