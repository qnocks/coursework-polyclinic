package ru.rostanin.polyclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.domain.Patient;
import ru.rostanin.polyclinic.services.AppointmentsService;
import ru.rostanin.polyclinic.services.PatientsService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientsController {

    private final PatientsService patientsService;
    private final AppointmentsService appointmentsService;

    @Autowired
    public PatientsController(PatientsService patientsService, AppointmentsService appointmentsService) {
        this.patientsService = patientsService;
        this.appointmentsService = appointmentsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("patients", patientsService.getAll());
        return "patients/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("patients", Collections.singletonList(patientsService.get(id)));
        Appointment appointment = appointmentsService.findByRegistrationNumber(id);
        if (appointment == null) return "patients/list";
        model.addAttribute("appointment", appointment);
        return "patients/search";
    }

    @GetMapping(params = "fullName")
    public String showByFullName(@RequestParam(value = "fullName") String fullName, Model model) {
        if (fullName != null) {
            model.addAttribute("patients", patientsService.findByFullName(fullName));
            return "patients/list";
        }
        return "patients/list";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("patient") Patient patient) {
        return "patients/new";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "patients/new";
        }
        patientsService.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable String id, Model model) {
        var patient = new Patient();
        var storedPatient = patientsService.get(id);
        patient.setId(storedPatient.getId());
        patient.setRegistrationNumber(storedPatient.getRegistrationNumber());
        patient.setFullName(storedPatient.getFullName());
        patient.setBirthDate(storedPatient.getBirthDate());
        patient.setAddress(storedPatient.getAddress());
        patient.setJob(storedPatient.getJob());
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) return "patients/edit";
        patientsService.update(patient.getRegistrationNumber(), patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        patientsService.delete(id);
        Appointment appointment = appointmentsService
                .getAll()
                .stream()
                .filter(a -> a.getRegistrationNumber().equals(id))
                .findFirst()
                .orElse(null);
        if (appointment == null) return "redirect:/patients";
        appointmentsService.delete(appointment.getFullName());
        return "redirect:/patients";
    }

    @GetMapping("/clear")
    public String deleteAll() {
        patientsService.deleteAll();
        appointmentsService.deleteAll();
        return "redirect:/patients";
    }

    @GetMapping("/persist")
    public String persistData() {
        patientsService.persist();
        return "redirect:/patients";
    }
}
