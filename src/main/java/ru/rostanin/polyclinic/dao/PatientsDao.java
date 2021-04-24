package ru.rostanin.polyclinic.dao;

import ru.rostanin.polyclinic.datastructures.HashTable;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.List;

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
    public Patient update(Patient entity) {
        // TODO: this HashTable's update method should probably return boolean or sth
        patients.update(entity.getRegistrationNumber(), entity);
        return entity;
    }

    @Override
    public void delete(String id) {
        patients.remove(id);
    }

    @Override
    public List<Patient> findAll() {
        // TODO: HashTable should provides list representation of itself
        return null;
    }
}
