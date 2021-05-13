package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.HashTable;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.List;

@Component
public class PatientsDao implements CrudDao<String, Patient> {

    private static long id = 0L;
    private final HashTable<String, Patient> patients = new HashTable<>();

    @Override
    public Patient save(Patient entity) {
        entity.setId(id++);
        patients.put(entity.getRegistrationNumber(), entity);
        return entity;
    }

    @Override
    public Patient find(String id) {
        return patients.get(id);
    }

    @Override
    public Patient update(String id, Patient entity) {
        entity.setId(patients.get(id).getId());
        if (patients.update(id, entity))
            return entity;
        return null;
    }

    @Override
    public void delete(String id) {
        patients.remove(id);
    }

    @Override
    public List<Patient> findAll() {
        return patients.list();
    }
}
