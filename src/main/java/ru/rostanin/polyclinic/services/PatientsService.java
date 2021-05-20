package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.dao.PatientsDao;
import ru.rostanin.polyclinic.domain.Patient;
import ru.rostanin.polyclinic.repositories.PatientsRepository;

import java.util.List;

@Service
public class PatientsService {

    private final CrudDao<String, Patient> patientsCrudDao;

    private final PatientsRepository patientsRepository;

    @Autowired
    public PatientsService(CrudDao<String, Patient> patientsCrudDao, PatientsRepository patientsRepository) {
        this.patientsCrudDao = patientsCrudDao;
        this.patientsRepository = patientsRepository;

        receiveDataFromDb();
    }

    public List<Patient> getAll() {
        return patientsCrudDao.findAll();
    }

    public Patient get(String id) {
        return patientsCrudDao.find(id);
    }

    public Patient save(Patient patient) {
        return patientsCrudDao.save(patient);
    }

    public Patient update(String id, Patient patient) {
         return patientsCrudDao.update(id, patient);
    }

    public void delete(String id) {
        patientsCrudDao.delete(id);
    }

    public void deleteAll() {
        PatientsDao patientsDao = (PatientsDao) this.patientsCrudDao;
        patientsDao.deleteAll();
    }

    public Patient findByRegistrationNumber(String registrationNumber) {
        PatientsDao patientsDao = (PatientsDao) this.patientsCrudDao;
        return patientsDao.findByRegistrationNumber(registrationNumber);
    }

    public List<Patient> findByFullName(String fullName) {
        PatientsDao patientsDao = (PatientsDao) this.patientsCrudDao;
        return patientsDao.findByFullName(fullName);
    }

    public void receiveDataFromDb() {
        List<Patient> patients = (List<Patient>) patientsRepository.findAll();
        for (var d : patients) patientsCrudDao.save(d);
    }

    public void persist() {
        patientsRepository.deleteAll();
        getAll().forEach(patientsRepository::save);
    }

}
