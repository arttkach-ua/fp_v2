package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.util.List;

public class CarBrandService {
    public static List<CarBrand> getAll() throws CarBrandRepoException {
        CarBrandRepoI brandRepo = RepositoryFactory.getCarBrandRepo();
        return brandRepo.getAll();
    }
}
