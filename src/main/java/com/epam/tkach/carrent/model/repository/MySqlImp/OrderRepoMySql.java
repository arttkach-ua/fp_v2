package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.repository.OrderRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class OrderRepoMySql implements OrderRepoI {
    private static final Logger logger = LogManager.getLogger(OrderRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;
    @Override
    public boolean addNew(Order order) throws OrderRepoException {
        final String QUERY = "insert into orders(client_id, car_id, manager_id, docements, day_counts, price, driver_price, rent_sum, damage_sum, damage_description,manager_comment) values (?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            mapOrderToStatement(pstmt, order);
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
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

    private void mapOrderToStatement(PreparedStatement pstmt, Order order) throws SQLException {
        pstmt.setInt(1,order.getClient().getID());
        pstmt.setInt(2,order.getCar().getID());
        pstmt.setInt(3,order.getManager().getID());
        pstmt.setString(4,order.getDocuments());
        pstmt.setInt(5, order.getDaysCount());
        pstmt.setDouble(6,order.getPrice());
        pstmt.setDouble(7,order.getDriverPrice());
        pstmt.setDouble(8,order.getRentSum());
        pstmt.setDouble(9,order.getDamageSum());
        pstmt.setString(10, order.getDamageDescription());
        pstmt.setString(11, order.getManagerComment());
    }
}
