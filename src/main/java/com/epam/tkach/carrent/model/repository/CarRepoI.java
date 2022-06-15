package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

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

    List<Car> getListForPagination(int currentPage, int recordsPerPage,LinkedHashMap<String, Object> filters, String orderBy) throws CarRepoException;

    /**
     * Method used for getting  count of rows with cars stored in database
     * @return Returns a count of rows with cars stored in database. Can be used for pagination page
     */
    int getCountInDB() throws CarRepoException;

    int getCountInDB(LinkedHashMap<String, Object> filters) throws CarRepoException;

    /**
     * Method gets car from db by ID
     * @return Optional with car statement if found and empty if not found
     * @throws CarRepoException
     */
    Optional<Car> getById(int id) throws CarRepoException;
    /**
     * Method that updates car record to database.
     * Search of car must by ID field, if ID empty
     * @return true if updated, false if not
     * @throws CarRepoException
     */
    boolean update(Car car, Connection con) throws CarRepoException;

}
