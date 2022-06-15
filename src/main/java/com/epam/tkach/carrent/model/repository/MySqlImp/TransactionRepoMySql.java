package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.repository.TransactionRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionRepoMySql implements TransactionRepoI {
    private static final Logger logger = LogManager.getLogger(TransactionRepoMySql.class);
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Transaction transaction) throws TransactionException {
        String QUERY = "insert into transactions(user_id, sum, description) values(?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,transaction.getUser().getID());
            pstmt.setDouble(2,transaction.getSum());
            pstmt.setString(3, transaction.getDescription());
            pstmt.executeUpdate();
            logger.debug("insert transaction was success");
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in TransactionRepo.addNew method", ex);
            throw new TransactionException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return success;
    }

    @Override
    public boolean addNew(Transaction transaction, Connection con) throws TransactionException {
        String QUERY = "insert into transactions(user_id, sum, description) values(?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,transaction.getUser().getID());
            pstmt.setDouble(2,transaction.getSum());
            pstmt.setString(3, transaction.getDescription());
            pstmt.executeUpdate();
            logger.debug("insert transaction was success");
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in TransactionRepo.addNew method", ex);
            throw new TransactionException(ex);
        } finally {
            connectionPool.close(null, pstmt,rs);
        }
        return success;
    }

    @Override
    public List<Transaction> getAll() throws TransactionException {
        return null;
    }

    @Override
    public double getUserBalance(int userId) throws TransactionException {
        String QUERY = "select user_id, sum(sum) from transactions where user_id=? group by user_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Double result = 0d;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,userId);
            rs = pstmt.executeQuery();
            while (rs.next()){
                result = rs.getDouble(2);
                break;
            }
        }catch (SQLException ex) {
            logger.error("Error in TransactionRepo.addNew method", ex);
            throw new TransactionException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return result;
    }
}
