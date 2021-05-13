package ru.rostanin.polyclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private Long id;

    @NotBlank(message = "Registration number cannot be blank")
    // TODO: regex
    private String registrationNumber;

    @NotBlank(message = "Patient's full name cannot be blank")
    private String fullName;

    //@NotNull(message = "This field cannot be null")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$",
            message = "Patient's birthdate should match pattern dd.mm.yyyy")
//    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d$",
//            message = "Patient's birthdate should match pattern dd.mm.yyyy")
    private String birthDate;

    @NotBlank(message = "This field cannot be blank")
    private String address;

    @NotBlank(message = "This field cannot be blank")
    private String job;

}
