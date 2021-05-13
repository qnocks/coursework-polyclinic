package ru.rostanin.polyclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Doctor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorsRestController {

    private final CrudDao<String, Doctor> doctors;

    @Autowired
    public DoctorsRestController(CrudDao<String, Doctor> doctors) {
        this.doctors = doctors;
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> list() {
        return new ResponseEntity<>(doctors.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Doctor> show(@PathVariable String id) {
        return new ResponseEntity<>(doctors.find(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctors.save(doctor), HttpStatus.OK);
    }

    // TODO: Doesn't work
    @PatchMapping("{id}")
    public ResponseEntity<Doctor> patch(@PathVariable String id, @RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctors.update(id, doctor), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Doctor> delete(@PathVariable String id) {
        doctors.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
