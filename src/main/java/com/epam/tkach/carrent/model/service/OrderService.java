package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.repository.OrderRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class OrderService {
    public static Order createNew(User client, Car car, int daysCount, String documents, boolean withDriver){
        Order order = new Order();
        order.setClient(client);
        order.setCar(car);
        order.setDaysCount(daysCount);
        order.setDocuments(documents);
        order.setPrice(car.getTariff().getRentPrice());
        order.setDriverPrice(car.getTariff().getDriverPrice());
        order.setWithDriver(withDriver);
        order.calculateSum();
        order.setStatus(OrderStatuses.NEW);
        return order;
    }
    public static void addNew(Order order) throws OrderRepoException, CarRepoException {
        //1.Creating new order
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        repo.addNew(order);
        //2.Setting car Status
        order.getCar().setAvailable(false);
        CarService.update(order.getCar());
    }

    public static int getCountInDB(LinkedHashMap<String, Object> filters) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        return repo.getCountInDB(filters);
    }

    public static List<Order> getList(int currentPage, int recordsPerPage,LinkedHashMap<String, Object> filters) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        return repo.getListForPaginationWithFilter(currentPage, recordsPerPage,filters);
    }
}
