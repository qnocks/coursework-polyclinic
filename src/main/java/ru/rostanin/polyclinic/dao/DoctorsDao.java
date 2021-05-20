package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.AvlTree;
import ru.rostanin.polyclinic.datastructures.util.Algorithm;
import ru.rostanin.polyclinic.domain.Doctor;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorsDao implements CrudDao<String, Doctor> {

    private static long id = 0L;
    private AvlTree<String, Doctor> doctors = new AvlTree<>();

    @Override
    public Doctor save(Doctor entity) {
        entity.setId(id++);
        if (doctors.insert(entity.getFullName(), entity))
            return entity;
        return null;
    }

    @Override
    public Doctor find(String id) {
        return doctors.contains(id);
    }

    @Override
    public Doctor update(String id, Doctor entity) {
        if (doctors.update(id, entity)) {
            return entity;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        doctors.remove(id);
    }

    public void deleteAll() {
        doctors = new AvlTree<>();
    }

    @Override
    public List<Doctor> findAll() {
        return doctors.getListInPostOrder(doctors.getRoot());
    }

    public Doctor findByFullName(String fullName) {
        return findAll().stream()
                .filter(d -> d.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
    }

    public List<Doctor> findBySpecialty(String specialty) {
        List<Doctor> result = new ArrayList<>();
        List<Doctor> list = findAll();

        for (var e : list) {
            if (Algorithm.naiveSearch(specialty, e.getSpecialty()))
                result.add(e);
        }
        return result;
    }
}
