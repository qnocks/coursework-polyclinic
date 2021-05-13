package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.domain.Doctor;

import java.util.List;

@Service
public class AppointmentsService {

    private final CrudDao<String, Appointment> appointmentsDao;

    @Autowired
    public AppointmentsService(CrudDao<String, Appointment> appointmentsDao) {
        this.appointmentsDao = appointmentsDao;
    }

    public List<Appointment> getAll() {
        return appointmentsDao.findAll();
    }

    public Appointment get(String id) {
        return appointmentsDao.find(id);
    }

    public Appointment save(Appointment appointment) {
        return appointmentsDao.save(appointment);
    }

    public Appointment update(String id, Appointment appointment) {
        return appointmentsDao.update(id, appointment);
    }

    public void delete(String id) {
        appointmentsDao.delete(id);
    }

}
