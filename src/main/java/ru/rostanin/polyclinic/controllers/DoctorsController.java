package ru.rostanin.polyclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.domain.Doctor;
import ru.rostanin.polyclinic.domain.Patient;
import ru.rostanin.polyclinic.services.AppointmentsService;
import ru.rostanin.polyclinic.services.DoctorsService;
import ru.rostanin.polyclinic.services.PatientsService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {

    private final DoctorsService doctorsService;
    private final PatientsService patientsService;
    private final AppointmentsService appointmentsService;


    @Autowired
    public DoctorsController(DoctorsService doctorsService, PatientsService patientsService, AppointmentsService appointmentsService) {
        this.doctorsService = doctorsService;
        this.patientsService = patientsService;
        this.appointmentsService = appointmentsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("doctors", doctorsService.getAll());
        return "doctors/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("doctors", List.of(doctorsService.get(id)));
        return "doctors/list";
    }

    @GetMapping(params = "specialty")
    public String showBySpecialty(@RequestParam(value = "specialty") String specialty, Model model) {
        model.addAttribute("doctors", doctorsService.findBySpecialty(specialty));
        return "doctors/list";
    }

    @GetMapping(params = "fullName")
    public String showByFullName(@RequestParam(value = "fullName") String fullName, Model model) {
        Doctor doctor = doctorsService.findByFullName(fullName);
        model.addAttribute("doctors", List.of(doctor));

        List<Appointment> appointments = appointmentsService.findByFullName(fullName);
        if (appointments.isEmpty()) return "doctors/list";

        List<Patient> patients = new ArrayList<>();
        for (var a : appointments) patients.add(patientsService.findByRegistrationNumber(a.getRegistrationNumber()));
        if (patients.isEmpty()) return "doctors/list";
        model.addAttribute("patients", patients);

        return "doctors/search";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("doctor") Doctor doctor) {
        return "doctors/new";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "doctors/new";
        }
        doctorsService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable String id, Model model) {
        var doctor = new Doctor();
        var storedDoctor = doctorsService.get(id);
        doctor.setId(storedDoctor.getId());
        doctor.setFullName(storedDoctor.getFullName());
        doctor.setSpecialty(storedDoctor.getSpecialty());
        doctor.setOfficeNumber(storedDoctor.getOfficeNumber());
        doctor.setSchedule(storedDoctor.getSchedule());
        model.addAttribute("doctor", doctor);
        return "doctors/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid Doctor doctor, BindingResult result) { // Doctor instance came from form
        if (result.hasErrors()) return "doctors/edit";
        doctorsService.update(doctor.getFullName(), doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        doctorsService.delete(id);
        appointmentsService.delete(id);
        return "redirect:/doctors";
    }

    @GetMapping("/clear")
    public String deleteAll() {
        doctorsService.deleteAll();
        appointmentsService.deleteAll();
        return "redirect:/doctors";
    }

    @GetMapping("/persist")
    public String persistData() {
        doctorsService.persist();
        return "redirect:/doctors";
    }

}
