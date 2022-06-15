package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class CarBrandService {
    public static List<CarBrand> getAll() throws CarBrandRepoException {
        CarBrandRepoI repo = RepositoryFactory.getCarBrandRepo();
        return repo.getAll();
    }

    public static void addNew(CarBrand brand) throws CarBrandRepoException {
        CarBrandRepoI repo = RepositoryFactory.getCarBrandRepo();
        repo.addNew(brand);
    }

    public static boolean checkIfExist(String name) throws CarBrandRepoException {
        CarBrandRepoI repo = RepositoryFactory.getCarBrandRepo();
        Optional<CarBrand> existingBrand = repo.findByName(name);
        return existingBrand.isPresent();
    }
}
