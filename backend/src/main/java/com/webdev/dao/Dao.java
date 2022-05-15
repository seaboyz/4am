package com.webdev.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    T add(T obj);

    Optional<T> get(Integer id);

    List<T> getAll();

    T update(T t);

    void delete(Integer id);

    void delete(T t);

}
