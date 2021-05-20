package ru.rostanin.polyclinic.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    @NotBlank(message = "This field cannot be blank")
    @Size(max = 25, message = "Full name's length must be up to 25 characters")
    private String fullName;

    @Column(name = "specialty")
    @NotBlank(message = "This field cannot be blank")
    private String specialty;

    @Column(name = "office_number")
    @NotNull(message = "This field can't be null")
    @Min(value = 1, message = "The office number cannot be less then 1")
    private Integer officeNumber;

    @Column(name = "schedule")
    @NotBlank(message = "This field cannot be blank")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]",
            message = "Doctor's schedule should match pattern HH:MM-HH:MM 24 hours")
    @Setter(AccessLevel.NONE)
    private String schedule;

    public void setSchedule(String schedule) {

        // 14:22-16:33
        String[] times = schedule.split("-"); // [14:22, 16:33]
        String[] startHoursAndMins = times[0].split(":"); // [14, 22]
        String[] endHoursAndMins = times[1].split(":"); // [16, 33]

        int startHours = Integer.parseInt(startHoursAndMins[0]);
        int startMins = Integer.parseInt(startHoursAndMins[1]);
        int endHours = Integer.parseInt(endHoursAndMins[0]);
        int endMins = Integer.parseInt(endHoursAndMins[1]);

        // 14 > 16
        if (startHours > endHours || ((startHours == endHours) && startMins > endMins)) {
            throw new IllegalArgumentException("Schedule is incorrect");
        }

        this.schedule = schedule;
    }
}
