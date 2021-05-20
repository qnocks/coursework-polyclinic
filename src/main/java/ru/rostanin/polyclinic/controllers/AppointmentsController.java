package ru.rostanin.polyclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.services.AppointmentsService;
import ru.rostanin.polyclinic.services.DoctorsService;
import ru.rostanin.polyclinic.services.PatientsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentsService appointmentsService;
    private final DoctorsService doctorsService;
    private final PatientsService patientsService;

    @Autowired
    public AppointmentsController(AppointmentsService appointmentsService,
                                  DoctorsService doctorsService,
                                  PatientsService patientsService) {
        this.appointmentsService = appointmentsService;
        this.doctorsService = doctorsService;
        this.patientsService = patientsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appointments", appointmentsService.getAll());
        return "appointments/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("appointments", List.of(appointmentsService.get(id)));
        return "appointments/list";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("appointment") Appointment appointment) {
        return "appointments/new";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Appointment appointment, BindingResult result) {
        if (result.hasErrors()) return "appointments/new";

        if (patientsService.findByRegistrationNumber(appointment.getRegistrationNumber()) == null) {
            result.rejectValue("registrationNumber", "registrationNumber","There is no patient with this registration number");
            return "appointments/new";
        }
        if (doctorsService.findByFullName(appointment.getFullName()) == null) {
            result.rejectValue("fullName", "fullName","There is no doctor with this name");
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

    @GetMapping(value = "/delete", params = {"registrationNumber", "fullName"})
    public String delete(@RequestParam(value = "registrationNumber") String registrationNumber,
                         @RequestParam(value = "fullName") String fullName) {
        appointmentsService.delete(registrationNumber, fullName);
        return "redirect:/appointments";
    }

    @GetMapping("/clear")
    public String deleteAll() {
        appointmentsService.deleteAll();
        return "redirect:/appointments";
    }

    @GetMapping("/persist")
    public String persistData() {
        appointmentsService.persist();
        return "redirect:/appointments";
    }
}
