package com.monfauna.MonFaunaAPI.service;

import com.monfauna.MonFaunaAPI.dao.AnimalDao;
import com.monfauna.MonFaunaAPI.dao.AnimalMeasurementDao;
import com.monfauna.MonFaunaAPI.dao.LocationDao;
import com.monfauna.MonFaunaAPI.dao.impl.DaoFactory;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.AnimalMeasurement;
import com.monfauna.MonFaunaAPI.model.Location;

import java.util.List;

public class AnimalService {

    private final AnimalDao animalDao;

    private final LocationDao locationDao;

    private final AnimalMeasurementDao animalMeasurementDao;

    public AnimalService() {
        this.animalDao = DaoFactory.getAnimalDao();
        this.locationDao = DaoFactory.getLocationDao();
        this.animalMeasurementDao = DaoFactory.getAnimalMeasurementDao();
    }

    public List<Animal> findAllByProject(Integer id) {
        return animalDao.findAllByProject(id);
    }

    public Animal findById(Integer id) throws NotFoundException {
        Animal animal = animalDao.findById(id);
        if (animal == null) {
            throw new NotFoundException("Animal Not Found");
        } else {
            return animal;
        }
    }


    public Animal save(Animal animal, Integer projectId) {
        Location locationSaved = locationDao.save(animal.getLocation());
        animal.setLocation(locationSaved);

        Animal animalSaved = animalDao.save(animal, projectId);

        for (AnimalMeasurement am : animal.getMeasurements()) {
            animalMeasurementDao.save(am, animalSaved.getId());
        }
        return findById(animalSaved.getId());

    }

    public Animal update(Animal animalToUpdate, Integer id) {
        Animal animal = findById(id);
        animalToUpdate.setId(animal.getId());
        animalToUpdate.setLocation(animal.getLocation());
        Animal animalUpdated = animalDao.update(animalToUpdate);
        return findById(animalUpdated.getId());
    }
}
