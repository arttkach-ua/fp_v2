package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;

import java.util.List;
import java.util.Optional;

/**
 * Interface that contains methods to work with car model brands id database
 * @author Tkach Artem
 */
public interface CarBrandRepoI {
    /**
     * Method used for getting car brands with pagination
     * @param currentPage
     * @param recordsPerPage
     * @return limited list of cars brands
     */
    List<CarBrand> getCarBrandsForPagination(int currentPage, int recordsPerPage) throws CarBrandRepoException;

    /**
     * Method used for getting  count of rows with car brands stored in database
     * @return Returns a count of rows with car brands stored in database. Can be used for pagination page
     */
    int getCountOfBrandsInDB() throws CarBrandRepoException;

    /**
     * Method returns element of carBrand,
     */
    Optional<CarBrand> findByID(int Id) throws CarBrandRepoException;

    /**
     *
     * @param carBrand
     * @return true is added, false if failed
     */
    boolean addNew(CarBrand carBrand) throws CarBrandRepoException;

    Optional<CarBrand> findByName(String name) throws CarBrandRepoException;

    List<CarBrand> getAll() throws CarBrandRepoException;
}
