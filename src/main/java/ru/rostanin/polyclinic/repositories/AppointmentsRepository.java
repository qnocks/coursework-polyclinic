package ru.rostanin.polyclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rostanin.polyclinic.domain.Appointment;

@Repository
public interface AppointmentsRepository extends CrudRepository<Appointment, Long> {
}
