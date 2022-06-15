package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.QueryBuilder;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.repository.OrderRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class OrderRepoMySql implements OrderRepoI {
    private static final Logger logger = LogManager.getLogger(OrderRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Order order, Connection con) throws OrderRepoException {
        boolean conIsNull = (con==null);
        final String QUERY = "insert into orders(client_id, car_id, manager_id, docements, day_counts, price, driver_price, rent_sum,manager_comment,status) values (?,?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            if (con==null) con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            mapOrderToStatement(pstmt, order);
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in OrderRepo.addNew method", ex);
            throw new OrderRepoException(ex);
        } finally {
            if (conIsNull){
                connectionPool.close(con, pstmt,null);
            }else{
                connectionPool.close(null, pstmt,null);
            }
        }

        return success;
    }

    @Override
    public boolean update(Order order, Connection con) throws OrderRepoException {
        final String QUERY = "update orders set client_id = ?, car_id = ?, manager_id=?, docements=?, day_counts=?, price=?, driver_price=?, rent_sum=?,manager_comment=?,status=? where id=?";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(QUERY);
            mapOrderToStatement(pstmt, order);
            pstmt.setInt(11, order.getID());
            pstmt.executeUpdate();
            logger.debug(order.getStatus());
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in OrderRepo.update method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(null, pstmt,null);
        }

        return success;
    }

    @Override
    public boolean update(Order order) throws OrderRepoException {
        final String QUERY = "update orders set client_id = ?, car_id = ?, manager_id=?, docements=?, day_counts=?, price=?, driver_price=?, rent_sum=?,manager_comment=?,status=? where id=?";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            mapOrderToStatement(pstmt, order);
            pstmt.setInt(11, order.getID());
            pstmt.executeUpdate();
            logger.debug(order.getStatus());
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in OrderRepo.update method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }

        return success;
    }

    @Override
    public List<Order> getListForPaginationWithFilter(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters) throws OrderRepoException {
        String query_ = "select orders.id, orders.client_id, orders.car_id, orders.manager_id, orders.docements, orders.day_counts, orders.price, orders.status, orders.with_driver, orders.manager_comment, orders.driver_price, orders.rent_sum from orders\n" +
                "left join cars c on orders.car_id = c.id";

        List<Order> entityList= new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            String query = QueryBuilder.buildQuery(query_, filters,"id", true);

            pstmt = con.prepareStatement(query);
            QueryBuilder.setParamsToPreparedStatement(pstmt,true,currentPage,recordsPerPage,filters);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entityList.add(Mapper.createOrderFromResultSet(rs));
            }
        } catch (SQLException | CarRepoException | UserRepoException ex) {
            logger.error("Error in OrderRepo.getListForPaginationWithFilter method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return entityList;
    }

    @Override
    public int getCountInDB(LinkedHashMap<String, Object> filters) throws OrderRepoException {
        String query_ = "select count(orders.id) from orders left join cars c on orders.car_id = c.id";
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            String query = QueryBuilder.buildQuery(query_, filters,null, false);
            pstmt = con.prepareStatement(query);
            QueryBuilder.setParamsToPreparedStatement(pstmt,false,0,0,filters);
            rs = pstmt.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in getCountInDB method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return count;
    }

    @Override
    public Optional<Order> getById(int id) throws OrderRepoException {
        final String QUERY = "select id, client_id, car_id, manager_id, docements, day_counts, price, status, with_driver, manager_comment, driver_price, rent_sum from orders where id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Order order = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                order = Mapper.createOrderFromResultSet(rs);
                break;
            }
        } catch (SQLException | CarRepoException | UserRepoException ex) {
            logger.error("Error in OrderRepoMySQL.getById method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(order);
    }

    private void mapOrderToStatement(PreparedStatement pstmt, Order order) throws SQLException {
        pstmt.setInt(1,order.getClient().getID());
        pstmt.setInt(2,order.getCar().getID());
        if (order.getManager()!=null) {
            pstmt.setInt(3,order.getManager().getID());
        }else{
            pstmt.setInt(3,0);
        }
        pstmt.setString(4,order.getDocuments());
        pstmt.setInt(5, order.getDaysCount());
        pstmt.setDouble(6,order.getPrice());
        pstmt.setDouble(7,order.getDriverPrice());
        pstmt.setDouble(8,order.getRentSum());
        pstmt.setString(9, order.getManagerComment());
        pstmt.setInt(10, order.getStatus().getValue());
    }




}
