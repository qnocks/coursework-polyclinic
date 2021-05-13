package ru.rostanin.polyclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rostanin.polyclinic.dao.CrudDao;
import ru.rostanin.polyclinic.dao.DoctorsDao;
import ru.rostanin.polyclinic.domain.Doctor;

import java.util.List;

@Service
public class DoctorsService {

    private final CrudDao<String, Doctor> doctorsDao;

    @Autowired
    public DoctorsService(CrudDao<String, Doctor> doctorsDao) {
        this.doctorsDao = doctorsDao;
    }

    public List<Doctor> getAll() {
        return doctorsDao.findAll();
    }

    public Doctor get(String id) {
        return doctorsDao.find(id);
    }

    public Doctor save(Doctor doctor) {
        return doctorsDao.save(doctor);
    }

    public Doctor update(String id, Doctor doctor) {
        return doctorsDao.update(id, doctor);
    }

    public void delete(String id) {
        doctorsDao.delete(id);
    }

}
