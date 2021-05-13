package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.AvlTree;
import ru.rostanin.polyclinic.domain.Doctor;

import java.util.List;

@Component
public class DoctorsDao implements CrudDao<String, Doctor> {

    private static long id = 0L;
    private final AvlTree<String, Doctor> doctors = new AvlTree<>();

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
        // entity.setId(doctors.contains(id).getId());
        if (doctors.update(id, entity)) {
            return entity;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        doctors.remove(id);
    }

    @Override
    public List<Doctor> findAll() {
        return doctors.getListInPostOrder(doctors.getRoot());
    }
}
