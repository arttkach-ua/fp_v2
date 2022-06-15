package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.command.client.AddNewOrder;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.InvoiceTypes;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.repository.OrderRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

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
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try {
            con.setAutoCommit(false);
            OrderRepoI repo = RepositoryFactory.getOrderRepo();
            repo.addNew(order, con);
            //2.Setting car Status
            order.getCar().setAvailable(false);
            CarService.update(order.getCar(), con);
            con.commit();
        } catch (SQLException e) {
            logger.error("Error while crating new order");
            logger.error(e);
            connectionPool.rollback(con);
        }
    }

    public static int getCountInDB(LinkedHashMap<String, Object> filters) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        return repo.getCountInDB(filters);
    }

    public static List<Order> getList(int currentPage, int recordsPerPage,LinkedHashMap<String, Object> filters) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        return repo.getListForPaginationWithFilter(currentPage, recordsPerPage,filters);
    }
    public static Order findById(int id) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        Optional<Order> opt = repo.getById(id);
        if (opt.isEmpty()) throw new OrderRepoException();
        return opt.get();
    }

    public static void update(Order order) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        repo.update(order);
    }

    public static void update(Order order, Connection con) throws OrderRepoException {
        OrderRepoI repo = RepositoryFactory.getOrderRepo();
        repo.update(order, con);
    }

    public static void declineOrder(int orderId, String comment) throws OrderRepoException, CarRepoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();

        Order order = findById(orderId);
        order.setStatus(OrderStatuses.CANCELED);
        order.setManagerComment(comment);
        try {
            con.setAutoCommit(false);
            OrderService.update(order, con);
            Car car = order.getCar();
            car.setAvailable(true);
            CarService.update(car,con);
        } catch (SQLException e) {
            logger.error("Error while declining order");
            logger.error(e);
            connectionPool.rollback(con);
        }
    }

    /**
     * Approves order
     * @param orderId
     * @throws OrderRepoException
     */
    public static void approveOrder(int orderId) throws OrderRepoException {
        Order order = OrderService.findById(orderId);
        order.setStatus(OrderStatuses.APPROVED);
        OrderService.update(order);
    }

    /**
     * Closes order with out damage.
     * @param orderId
     * @throws OrderRepoException
     * @throws CarRepoException
     */
    public static void closeOrder(int orderId) throws OrderRepoException, CarRepoException {
        //Starting all in transaction
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try {
            con.setAutoCommit(false);
//          1.Setting order status
            Order order = OrderService.findById(orderId);
            order.setStatus(OrderStatuses.COMPLETE);
            OrderService.update(order, con);
//          2. Setting car available to true
            Car car = order.getCar();
            car.setAvailable(true);
            CarService.update(car, con);
            con.commit();
            connectionPool.close(con);
        } catch (SQLException e) {
            logger.error("Error while closing order");
            logger.error(e);
            connectionPool.rollback(con);
        }
    }

    /**
     * Closes order when car is damaged. Also creates invoice for damage
     * @param orderId
     * @param Damage
     * @throws OrderRepoException
     * @throws CarRepoException
     */
    public static void closeOrderWithDamage(int orderId, String damage, double amount) throws OrderRepoException, CarRepoException, InvoiceRepoException {
        //Starting all in transaction
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try {
            con.setAutoCommit(false);
//          1.Setting order status
            Order order = OrderService.findById(orderId);
            order.setStatus(OrderStatuses.HASDAMAGE);
            order.setManagerComment(damage);
            OrderService.update(order, con);
//          2. Setting car available to true
            Car car = order.getCar();
            car.setAvailable(true);
            CarService.update(car, con);
            //3. Create invoice
            InvoiceService.createNewAndSaveInDB(orderId, InvoiceTypes.DAMAGE, amount, damage);
            con.commit();
            connectionPool.close(con);

        } catch (SQLException e) {
            logger.error("Error while closing order");
            logger.error(e);
            connectionPool.rollback(con);
        }
    }
}
