package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.QueryBuilder;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Invoice;
import com.epam.tkach.carrent.model.repository.InvoiceRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class InvoiceRepoMySql implements InvoiceRepoI {
    private static final Logger logger = LogManager.getLogger(InvoiceRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Invoice invoice) throws InvoiceRepoException {
        final String QUERY = "insert into invoices(client_id, type, order_id, description, amount, paid) values(?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,invoice.getClient().getID());
            pstmt.setInt(2,invoice.getType().getValue());
            pstmt.setInt(3,invoice.getOrder().getID());
            pstmt.setString(4,invoice.getDescription());
            pstmt.setDouble(5, invoice.getAmount());
            pstmt.setBoolean(6, invoice.isPaid());
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in InvoiceRepoMySql.addNew method", ex);
            throw new InvoiceRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }

        return success;
    }

//    @Override
//    public boolean update(Invoice invoice) throws InvoiceRepoException {
//        final String QUERY = "update invoices set client_id=?, type=?, order_id=?, description=?, amount=?, paid=? where id=?";
//        boolean success = false;
//        PreparedStatement pstmt = null;
//        try{
//            con = connectionPool.getConnection();
//            pstmt = con.prepareStatement(QUERY);
//            pstmt.setInt(1,invoice.getClient().getID());
//            pstmt.setInt(2,invoice.getType().getValue());
//            pstmt.setInt(3,invoice.getOrder().getID());
//            pstmt.setString(4,invoice.getDescription());
//            pstmt.setDouble(5, invoice.getAmount());
//            pstmt.setBoolean(6, invoice.isPaid());
//            pstmt.setInt(7, invoice.getID());
//
//            pstmt.executeUpdate();
//            success=true;
//        }catch (SQLException ex) {
//            logger.error("Error in InvoiceRepoMySql.update method", ex);
//            throw new InvoiceRepoException(ex);
//        } finally {
//            connectionPool.close(con, pstmt,null);
//        }
//
//        return success;
//    }

    @Override
    public boolean update(Invoice invoice, Connection con) throws InvoiceRepoException {
        boolean conIsNull = (con==null);
        final String QUERY = "update invoices set client_id=?, type=?, order_id=?, description=?, amount=?, paid=? where id=?";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            if (con==null) con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,invoice.getClient().getID());
            pstmt.setInt(2,invoice.getType().getValue());
            pstmt.setInt(3,invoice.getOrder().getID());
            pstmt.setString(4,invoice.getDescription());
            pstmt.setDouble(5, invoice.getAmount());
            pstmt.setBoolean(6, invoice.isPaid());
            pstmt.setInt(7, invoice.getID());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in InvoiceRepoMySql.update method", ex);
            throw new InvoiceRepoException(ex);
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
    public boolean delete(Invoice invoice) throws InvoiceRepoException {
        return false;
    }

    @Override
    public int getCountInDB(LinkedHashMap<String, Object> filters) throws InvoiceRepoException {
        String query_ = "select count(invoices.id) from invoices";
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            String query = QueryBuilder.buildQuery(query_, filters,null, false);
            pstmt = con.prepareStatement(query);
            logger.debug(query);
            QueryBuilder.setParamsToPreparedStatement(pstmt,false,0,0,filters);

            rs = pstmt.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in InvoiceRepoMySql.getCountInDB method", ex);
            throw new InvoiceRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return count;
    }

    @Override
    public List<Invoice> getListForPagination(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws InvoiceRepoException {
        String query_ = "select id, client_id, type, order_id, description, amount, paid, timestamp from invoices";
        List<Invoice> entityList= new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            String query = QueryBuilder.buildQuery(query_, filters, orderBy, true);
            logger.debug(query);
            pstmt = con.prepareStatement(query);

            QueryBuilder.setParamsToPreparedStatement(pstmt,true,currentPage,recordsPerPage,filters);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entityList.add(Mapper.createInvoiceFromResultSet(rs));
            }
        } catch (SQLException | UserRepoException | OrderRepoException ex) {
            logger.error("Error InvoiceRepo.getListForPagination method", ex);
            throw new InvoiceRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return entityList;
    }

    @Override
    public Optional<Invoice> getById(int id) throws InvoiceRepoException {
        if (id<0) return Optional.empty();

        final String QUERY = "select id, client_id, type, order_id, description, amount, paid, timestamp from invoices where id=?";
        Invoice entity = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entity = Mapper.createInvoiceFromResultSet(rs);
            }
        } catch (SQLException | UserRepoException | OrderRepoException ex) {
            logger.error("Error in InvoiceRepoMySql.getById method", ex);
            throw new InvoiceRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.of(entity);
    }
}
