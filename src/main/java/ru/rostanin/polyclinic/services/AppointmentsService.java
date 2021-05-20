package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.AppointmentsDao;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Appointment;
import ru.rostanin.polyclinic.repositories.AppointmentsRepository;

import java.util.List;

@Service
public class AppointmentsService {

    private final CrudDao<String, Appointment> appointmentsCrudDao;

    private final AppointmentsRepository appointmentsRepository;

    @Autowired
    public AppointmentsService(CrudDao<String, Appointment> appointmentsDao, AppointmentsRepository appointmentsRepository) {
        this.appointmentsCrudDao = appointmentsDao;
        this.appointmentsRepository = appointmentsRepository;

        receiveDataFromDb();
    }

    public List<Appointment> getAll() {
        return appointmentsCrudDao.findAll();
    }

    public Appointment get(String id) {
        return appointmentsCrudDao.find(id);
    }

    public Appointment save(Appointment appointment) {

        return appointmentsCrudDao.save(appointment);
    }

    public Appointment update(String id, Appointment appointment) {
        return appointmentsCrudDao.update(id, appointment);
    }

    public void delete(String id) {
        appointmentsCrudDao.delete(id);
    }

    public void delete(String registrationNumber, String fullName) {
        AppointmentsDao appointmentsDao = (AppointmentsDao) this.appointmentsCrudDao;
        appointmentsDao.delete(registrationNumber, fullName);
    }

    public void deleteAll() {
        AppointmentsDao appointmentsDao = (AppointmentsDao) this.appointmentsCrudDao;
        appointmentsDao.deleteAll();
    }

    public List<Appointment> findByFullName(String fullName) {
        AppointmentsDao appointmentsDao = (AppointmentsDao) this.appointmentsCrudDao;
        return appointmentsDao.findByFullName(fullName);
    }

    public Appointment findByRegistrationNumber(String registrationNumber) {
        AppointmentsDao appointmentsDao = (AppointmentsDao) this.appointmentsCrudDao;
        return appointmentsDao.findByRegistrationNumber(registrationNumber);
    }

    public void receiveDataFromDb() {
        List<Appointment> appointments = (List<Appointment>) appointmentsRepository.findAll();
        for (var d : appointments) appointmentsCrudDao.save(d);
    }

    public void persist() {
        appointmentsRepository.deleteAll();
        getAll().forEach(appointmentsRepository::save);
    }


}
