package ru.rostanin.polyclinic.dao;

import org.springframework.stereotype.Component;
import ru.rostanin.polyclinic.datastructures.MySkipList;
import ru.rostanin.polyclinic.domain.Appointment;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentsDao implements CrudDao<String, Appointment> {

    private static long id = 0L;
//    private final SkipList<String, Appointment> appointments = new SkipList<>();
//    private final HashMap<String, Appointment> appointments = new HashMap<>();
    private final MySkipList<String, Appointment> appointments = new MySkipList<>();

    @Override
    public Appointment save(Appointment entity) {
        entity.setId(id++);
//        appointments.put(entity.getFullName(), entity);
        appointments.add(entity.getFullName(), entity);
//        appointments.insert(entity.getFullName(), entity);
        return entity;
    }

    @Override
    public Appointment find(String id) {
//        return appointments.get(id);
        return appointments.get(id);
//        return appointments.find(id);
    }

    @Override
    public Appointment update(String id, Appointment entity) {
//        appointments.add(id, entity);
        appointments.update(id, entity);
        return entity;
    }

    @Override
    public void delete(String id) {
        appointments.remove(id);
//        appointments.remove(find(id)); list
//        appointments.delete(id);
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        for (var a : appointments) {
            if (a != null) list.add(a);
        }
        return list;
//        return appointments.list();
    }
}
