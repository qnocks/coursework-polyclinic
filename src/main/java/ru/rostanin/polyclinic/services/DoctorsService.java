package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.dao.DoctorsDao;
import ru.rostanin.polyclinic.domain.Doctor;
import ru.rostanin.polyclinic.repositories.DoctorsRepository;

import java.util.List;

@Service
public class DoctorsService {

    private final CrudDao<String, Doctor> doctorsCrudDao;

    private final DoctorsRepository doctorsRepository;


    @Autowired
    public DoctorsService(CrudDao<String, Doctor> doctorsCrudDao, DoctorsRepository doctorsRepository) {
        this.doctorsCrudDao = doctorsCrudDao;
        this.doctorsRepository = doctorsRepository;

        receiveDataFromDb();
    }

    public List<Doctor> getAll() {
        return doctorsCrudDao.findAll();
    }

    public Doctor get(String id) {
        return doctorsCrudDao.find(id);
    }

    public Doctor save(Doctor doctor) {
        return doctorsCrudDao.save(doctor);
    }

    public Doctor update(String id, Doctor doctor) {
        return doctorsCrudDao.update(id, doctor);
    }

    public void delete(String id) {
        doctorsCrudDao.delete(id);
    }

    public void deleteAll() {
        DoctorsDao doctorsDao = (DoctorsDao) this.doctorsCrudDao;
        doctorsDao.deleteAll();
    }

    public Doctor findByFullName(String fullName) {
        DoctorsDao doctorsDao = (DoctorsDao) this.doctorsCrudDao;
        return doctorsDao.findByFullName(fullName);
    }

    public List<Doctor> findBySpecialty(String specialty) {
        DoctorsDao doctorsDao = (DoctorsDao) this.doctorsCrudDao;
        return doctorsDao.findBySpecialty(specialty);
    }

    public void receiveDataFromDb() {
        List<Doctor> doctors = (List<Doctor>) doctorsRepository.findAll();
        for (var d : doctors) doctorsCrudDao.save(d);
    }

    public void persist() {
        doctorsRepository.deleteAll();
        getAll().forEach(doctorsRepository::save);
    }

}
