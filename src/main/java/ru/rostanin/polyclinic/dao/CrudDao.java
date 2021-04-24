package ru.rostanin.polyclinic.dao;

import java.util.List;

public interface CrudDao<ID, T> {

    T save(T entity); // C
    T find(ID id); // R
    T update(T entity); // U
    void delete(ID id); // D

    List<T> findAll();
}
