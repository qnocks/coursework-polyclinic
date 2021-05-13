package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.List;

@Service
public class PatientsService {

    private final CrudDao<String, Patient> patientsDao;

    @Autowired
    public PatientsService(CrudDao<String, Patient> patientsDao) {
        this.patientsDao = patientsDao;
    }

    public List<Patient> getAll() {
        return patientsDao.findAll();
    }

    public Patient get(String id) {
        return patientsDao.find(id);
    }

    public Patient save(Patient patient) {
        return patientsDao.save(patient);
    }

    public Patient update(String id, Patient patient) {
         return patientsDao.update(id, patient);
    }

    public void delete(String id) {
        patientsDao.delete(id);
    }

}
