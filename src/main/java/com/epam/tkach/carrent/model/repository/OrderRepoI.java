package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.entity.Order;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

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
     * Adds new order in dataBase
     * @param order
     * @return
     * @throws OrderRepoException
     */
    boolean addNew(Order order) throws OrderRepoException;
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
}
