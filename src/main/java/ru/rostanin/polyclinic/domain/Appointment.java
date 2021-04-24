package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class Appointment {

    @NotNull(message = "This field can't be null")
    private Long id;

    @NotBlank(message = "Registration number cannot be blank")
    private String registrationNumber;

    @NotBlank(message = "This field cannot be blank")
    @Size(max = 25, message = "Full name's length must be up to 25 characters")
    private String fullName;

    @NotBlank(message = "This field cannot be blank")
    @Pattern(regexp = "^([0-2][0-9]||3[0-1]).(0[0-9]||1[0-2]).([0-9][0-9])?[0-9][0-9]$",
            message = "Date of appointment should match pattern dd.mm.yyyy")
    private String date;

    @NotBlank(message = "This field cannot be blank")
    private String time;

}
