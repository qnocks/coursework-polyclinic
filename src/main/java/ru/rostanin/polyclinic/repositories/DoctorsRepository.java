package ru.rostanin.polyclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rostanin.polyclinic.domain.Doctor;

@Repository
public interface DoctorsRepository extends CrudRepository<Doctor, Long> {
}
