package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.HashTable;
import ru.rostanin.polyclinic.datastructures.util.Algorithm;
import ru.rostanin.polyclinic.domain.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientsDao implements CrudDao<String, Patient> {

    private static long id = 0L;
    private HashTable<String, Patient> patients = new HashTable<>();

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

    public void deleteAll() {
        patients = new HashTable<>();
    }

    @Override
    public List<Patient> findAll() {
        return patients.list();
    }

    public Patient findByRegistrationNumber(String registrationNumber) {
        List<Patient> list = findAll();
        return list.stream()
                .filter(p -> p.getRegistrationNumber().equals(registrationNumber))
                .findFirst()
                .orElse(null);
    }

    public List<Patient> findByFullName(String fullName) {
        List<Patient> result = new ArrayList<>();
        List<Patient> list = findAll();

        for (var e : list) {
            if (Algorithm.naiveSearch(fullName, e.getFullName()))
                result.add(e);
        }

        return result;
    }
}
