package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.LinkedList;
import ru.rostanin.polyclinic.datastructures.MySkipList;
import ru.rostanin.polyclinic.domain.Appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentsDao implements CrudDao<String, Appointment> {

    private static long id = 0L;
//    private final MySkipList<String, Appointment> appointments = new MySkipList<>();
    private LinkedList<String, Appointment> appointments = new LinkedList<>();

    @Override
    public Appointment save(Appointment entity) {
        entity.setId(id++);
        appointments.add(entity.getFullName(), entity);
        return entity;
    }

    @Override
    public Appointment find(String id) {
        for (var e : appointments) {
            if (e.getFullName().equals(id))
                return e;
        }
        return null;
//        return appointments.get(id);
    }

    @Override
    public Appointment update(String id, Appointment entity) {
//        appointments.add(id, entity);
        appointments.update(id, entity);
        return entity;
    }

    @Override
    public void delete(String id) {
//        appointments.remove(id);
        List<Appointment> collect = findAll().stream()
                .filter(a -> a.getFullName().equals(id))
                .collect(Collectors.toList());
        collect.forEach(a -> delete(a.getRegistrationNumber(), id));
    }

    public void delete(String registrationNumber, String fullName) {

        Appointment appointment = null;
        for (var a : findAll()) {
            System.out.println();
            System.out.println("reg.num=" + registrationNumber);
            System.out.println("a.reg.num=" + a.getRegistrationNumber());
            System.out.println(registrationNumber.equals(a.getRegistrationNumber()));
            System.out.println("fullname=" + fullName);
            System.out.println("a.fullname=" + a.getFullName());
            System.out.println(fullName.equals(a.getFullName()));
            if (a.getFullName().equals(fullName) && a.getRegistrationNumber().equals(registrationNumber)) {
                appointment = a;
                appointments.remove(fullName, appointment);
                return;
            }
        }
    }

    public void deleteAll() {
        appointments = new LinkedList<>();
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        for (var a : appointments) if (a != null) list.add(a);
        return list;
    }

    public List<Appointment> findByFullName(String fullName) {
        return findAll().stream()
                .filter(a -> a.getFullName().equals(fullName))
                .collect(Collectors.toList());
    }

    public Appointment findByRegistrationNumber(String registrationNumber) {
        return findAll().stream()
                .filter(a -> a.getRegistrationNumber().equals(registrationNumber))
                .findFirst()
                .orElse(null);
    }
}
