package com.monfauna.MonFaunaAPI.dao;


import java.util.List;

interface Crud<T> {
    T save(T entity);
    List<T> findAll();
    T findById(Integer id);
    T update(T entity);
    void deleteById(Integer id);
}
