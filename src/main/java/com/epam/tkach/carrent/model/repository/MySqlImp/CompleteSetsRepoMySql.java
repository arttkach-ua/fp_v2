package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.repository.CompleteSetsRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompleteSetsRepoMySql implements CompleteSetsRepoI {
    private static final Logger logger = LogManager.getLogger(CompleteSetsRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public int getCountInDb() throws CompleteSetsRepoException {
        final String QUERY = "select count(id) from complete_sets";
        int count = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in CompleteSetsRepoMySql.getCountInDb method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }
        return count;
    }

    @Override
    public List<CompleteSet> getListForPagination(int currentPage, int recordsPerPage) throws CompleteSetsRepoException {
        final String QUERY = "select id, name, model_id, body_style_id, transmission_id, fuel_type_id, engine from complete_sets limit ?,?";
        List<CompleteSet> entityList= new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entityList.add(Mapper.createCompleteSetFromResultSet(rs));
            }
        } catch (SQLException | CarModelRepoException ex) {
            logger.error("Error in getListForPagination method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return entityList;
    }

    @Override
    public boolean addNew(CompleteSet completeSet) throws CompleteSetsRepoException {
        final String QUERY = "insert into complete_sets(name, model_id, body_style_id, transmission_id, fuel_type_id, engine) values(?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,completeSet.getName());
            pstmt.setInt(2, completeSet.getCarModel().getID());
            pstmt.setInt(3, completeSet.getBodyStyle().getValue());
            pstmt.setInt(4, completeSet.getTransmission().getValue());
            pstmt.setInt(5, completeSet.getFuelType().getValue());
            pstmt.setDouble(6, completeSet.getEngine());
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }

        return success;
    }

    @Override
    public Optional<CompleteSet> getByID(int id) throws CompleteSetsRepoException {
        if (id<0) return Optional.empty();

        final String QUERY = "select id, name, model_id, body_style_id, transmission_id, fuel_type_id, engine from complete_sets where id=?";
        CompleteSet entity = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entity = Mapper.createCompleteSetFromResultSet(rs);
            }
        } catch (SQLException | CarModelRepoException ex) {
            logger.error("Error in getByID method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.of(entity);
    }

    @Override
    public boolean update(CompleteSet completeSet) throws CompleteSetsRepoException {
        boolean success = false;
        final String QUERY = "update complete_sets set name = ?, model_id = ?, body_style_id = ?, transmission_id = ?, fuel_type_id = ?, engine = ? where id=?";
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, completeSet.getName());
            pstmt.setInt(2, completeSet.getCarModel().getID());
            pstmt.setInt(3, completeSet.getBodyStyle().getValue());
            pstmt.setInt(4, completeSet.getTransmission().getValue());
            pstmt.setInt(5, completeSet.getFuelType().getValue());
            pstmt.setDouble(6, completeSet.getEngine());
            pstmt.setInt(7,completeSet.getID());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in CompleteSetsRepo.update method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return success;
    }

    @Override
    public List<CompleteSet> findByCarModelId(int id) throws CompleteSetsRepoException {
        final String QUERY = "select id, name, model_id, body_style_id, transmission_id, fuel_type_id, engine from complete_sets where model_id=?";
        List<CompleteSet> entityList= new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                entityList.add(Mapper.createCompleteSetFromResultSet(rs));
            }
        } catch (SQLException | CarModelRepoException ex) {
            logger.error("Error in findByCarModelId method", ex);
            throw new CompleteSetsRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return entityList;
    }
}
