package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.util.List;
import java.util.Optional;

public class CarModelService {

    public static CarModel findByID(int id) throws CarModelRepoException {
        CarModelRepoI repo = RepositoryFactory.getCarModelRepo();
        Optional<CarModel> opt =  repo.findByID(id);
        if (opt.isEmpty()) throw new CarModelRepoException();
        return opt.get();
    }

    public static void addNew(int brandId, String modelName, int classId) throws CarModelRepoException{
        CarModelRepoI repo = RepositoryFactory.getCarModelRepo();
        repo.addNew(brandId,classId,modelName);
    }
    public static int genCountInDB() throws CarModelRepoException{
        CarModelRepoI repo = RepositoryFactory.getCarModelRepo();
        return repo.getCountOfModelsInDB();
    }
    public static List<CarModel> getCarModelsListForPagination(int currentPage, int recordsPerPage) throws CarModelRepoException {
        CarModelRepoI repo = RepositoryFactory.getCarModelRepo();
        return repo.getCarModelsListForPagination(currentPage, recordsPerPage);
    }
}
