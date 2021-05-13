package ru.rostanin.polyclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Doctor;
import ru.rostanin.polyclinic.services.DoctorsService;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {

    private final DoctorsService doctorsService;

    @Autowired
    public DoctorsController(DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("doctors", doctorsService.getAll());
        return "doctors/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("doctors", Collections.singletonList(doctorsService.get(id)));
        return "doctors/list";
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
        return "redirect:/doctors";
    }
}
