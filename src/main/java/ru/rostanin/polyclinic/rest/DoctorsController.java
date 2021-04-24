package ru.rostanin.polyclinic.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Doctor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorsController {

    @GetMapping
    public ResponseEntity<List<Doctor>> list() {
        return new ResponseEntity<List<Doctor>>(
                Arrays.asList(
                        new Doctor(0L, "Hello", "sdad", 14, "14:00-16:00")), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Doctor> show(@PathVariable String id) {
        return null;
    }

}
