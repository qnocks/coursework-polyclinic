package ru.rostanin.polyclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.services.AppointmentsService;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentsService appointmentsService;

    @Autowired
    public AppointmentsController(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appointments", appointmentsService.getAll());
        return "appointments/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("appointments", Collections.singletonList(appointmentsService.get(id)));
        return "appointments/list";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("appointment") Appointment appointment) {
        return "appointments/new";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Appointment appointment, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors() );
            return "appointments/new";
        }
        appointmentsService.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable String id, Model model) {
        var appointment = new Appointment();
        var storedAppointment = appointmentsService.get(id);
        if (storedAppointment.getId() != null) appointment.setId(storedAppointment.getId());
        if (storedAppointment.getRegistrationNumber() != null) appointment.setRegistrationNumber(storedAppointment.getRegistrationNumber());
        if (storedAppointment.getFullName() != null) appointment.setFullName(storedAppointment.getFullName());
        if (storedAppointment.getDate() != null) appointment.setDate(storedAppointment.getDate());
        if (storedAppointment.getTime() != null) appointment.setTime(storedAppointment.getTime());
        model.addAttribute("appointment", appointment);
        return "appointments/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid Appointment appointment, BindingResult result) { // Doctor instance came from form
        if (result.hasErrors()) return "appointments/edit";
        appointmentsService.update(appointment.getFullName(), appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        appointmentsService.delete(id);
        return "redirect:/appointments";
    }

}
