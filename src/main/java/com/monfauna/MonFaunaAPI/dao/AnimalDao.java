package com.monfauna.MonFaunaAPI.dao;

import com.monfauna.MonFaunaAPI.model.Animal;

import java.util.List;

public interface AnimalDao {
    Animal save(Animal animal, Integer projectId);
    List<Animal> findAll();
    List<Animal> findAllByProject(Integer idProject);
    Animal findById(Integer id);
    Animal update(Animal entity);
    void deleteById(Integer id);
}
