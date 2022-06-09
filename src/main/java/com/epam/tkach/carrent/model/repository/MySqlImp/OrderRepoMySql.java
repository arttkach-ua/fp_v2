package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.QueryBuilder;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Car;
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
        return false;
    }

    @Override
    public boolean addNew(Order order) throws OrderRepoException {
        final String QUERY = "insert into orders(client_id, car_id, manager_id, docements, day_counts, price, driver_price, rent_sum,manager_comment) values (?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            mapOrderToStatement(pstmt, order);
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in OrderRepo.addNew method", ex);
            throw new OrderRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }

        return success;
    }

    @Override
    public boolean update(Order order) throws OrderRepoException {
        return false;
    }

    @Override
    public List<Order> getListForPaginationWithFilter(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters) throws OrderRepoException {
        String query_ = "select id, client_id, car_id, manager_id, docements, day_counts, price, status, with_driver, manager_comment, driver_price, rent_sum from orders";

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
        String query_ = "select count(id) from orders";
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
    }




}
