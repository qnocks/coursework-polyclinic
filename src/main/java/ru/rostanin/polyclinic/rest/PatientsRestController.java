package ru.rostanin.polyclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientsRestController {

    private final CrudDao<String, Patient> patients;

    @Autowired
    public PatientsRestController(CrudDao<String, Patient> patients) {
        this.patients = patients;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> list() {
        return new ResponseEntity<>(patients.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> show(@PathVariable String id) {
        return new ResponseEntity<>(patients.find(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        return new ResponseEntity<>(patients.save(patient), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Patient> patch(@PathVariable String id, @RequestBody Patient patient) {
        return new ResponseEntity<>(patients.update(id, patient), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Patient> delete(@PathVariable String id) {
        patients.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
