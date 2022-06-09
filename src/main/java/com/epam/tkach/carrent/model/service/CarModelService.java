package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.util.Optional;

public class CarModelService {

    public static CarModel findByID(int id) throws CarModelRepoException {
        CarModelRepoI repo = RepositoryFactory.getCarModelRepo();
        Optional<CarModel> opt =  repo.findByID(id);
        if (opt.isEmpty()) throw new CarModelRepoException();
        return opt.get();
    }
}
