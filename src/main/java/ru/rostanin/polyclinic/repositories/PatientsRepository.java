package ru.rostanin.polyclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rostanin.polyclinic.domain.Patient;

@Repository
public interface PatientsRepository extends CrudRepository<Patient, Long> {
}
