package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;

import java.util.List;

/**
 * Interface that contain methods with car
 */
public interface CarRepoI {

    /**
     *
     * @param car - entity of car Class
     * @return true if adden new, false if was error
     * @throws CarRepoException
     */
    boolean addNew(Car car) throws CarRepoException;
    /**
     * Method used for getting cars with pagination
     * @param currentPage
     * @param recordsPerPage
     * @return limited list of cars
     */
    List<Car> getListForPagination(int currentPage, int recordsPerPage) throws CarRepoException;

    /**
     * Method used for getting  count of rows with cars stored in database
     * @return Returns a count of rows with cars stored in database. Can be used for pagination page
     */
    int getCountInDB() throws CarRepoException;



}
