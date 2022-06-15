package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.entity.Order;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public interface OrderRepoI {
    /**
     * Adds new order in dataBase
     * @param order
     * @param con - Connection. Needed to transaction
     * @return
     * @throws OrderRepoException
     */
    boolean addNew(Order order, Connection con) throws OrderRepoException;
    /**
     * Updates existing order in dataBase. Must be user in manual commit mode
     * @param order
     * @return
     * @throws OrderRepoException
     */
    boolean update(Order order, Connection con) throws OrderRepoException;

    /**
     * Updates existing order in dataBase
     * @param order
     * @return
     * @throws OrderRepoException
     */
    boolean update(Order order) throws OrderRepoException;

    /**
     * Gets list of orders. List can be filtered by values in map
     * @param currentPage
     * @param recordsPerPage
     * @param filters -
     * @return
     * @throws OrderRepoException
     */
    List<Order> getListForPaginationWithFilter(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters) throws OrderRepoException;

    int getCountInDB(LinkedHashMap<String, Object> filters) throws OrderRepoException;

    /**
     * Method gets order from db by ID
     * @return Optional with order statement if found and empty if not found
     * @throws OrderRepoException
     */
    Optional<Order> getById(int id) throws OrderRepoException;
}
