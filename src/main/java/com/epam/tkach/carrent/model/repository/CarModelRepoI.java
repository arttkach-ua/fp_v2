package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelRepoI {
    /**
     * Method used for getting car brands with pagination
     * @param currentPage
     * @param recordsPerPage
     * @return limited list of cars models
     */
    List<CarModel> getCarModelsListForPagination(int currentPage, int recordsPerPage) throws CarModelRepoException;

    /**
     * Method used for getting  count of rows with car models stored in database
     * @return Returns a count of rows with car models stored in database. Can be used for pagination page
     */
    int getCountOfModelsInDB() throws CarModelRepoException;

    boolean addNew(int carBrandID, int carClassID, String modelName) throws CarModelRepoException;

    Optional<CarModel> findByNameAndBrand(int carBrandID, String name) throws CarModelRepoException;

    List<CarModel> getModelsByBrand(int carBrandID) throws CarModelRepoException;

    /**
     * Method search for existing car model in DB.
     * @param ID - id of car model
     * @return Optional with car model class
     * @throws CarModelRepoException
     */
    Optional<CarModel> findByID(int ID) throws CarModelRepoException;
}
