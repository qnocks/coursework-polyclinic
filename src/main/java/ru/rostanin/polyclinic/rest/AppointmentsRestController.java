package ru.rostanin.polyclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Appointment;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentsRestController {

    private final CrudDao<String, Appointment> appointments;

    @Autowired
    public AppointmentsRestController(CrudDao<String, Appointment> appointments) {
        this.appointments = appointments;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> list() {
        return new ResponseEntity<>(appointments.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> show(@PathVariable String id) {
        return new ResponseEntity<>(appointments.find(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointments.save(appointment), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Appointment> patch(@PathVariable String id, @RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointments.update(id, appointment), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Appointment> delete(@PathVariable String id) {
        appointments.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
