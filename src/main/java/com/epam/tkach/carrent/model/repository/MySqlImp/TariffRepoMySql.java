package com.epam.tkach.carrent.model.repository.MySqlImp;
import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.repository.TariffRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TariffRepoMySql implements TariffRepoI {
    private static final Logger logger = LogManager.getLogger(TariffRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Tariff tariff) throws TariffException {
        final String QUERY = "insert into tariffs(name, rent_price, driver_price) values(?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,tariff.getName());
            pstmt.setDouble(2,tariff.getRentPrice());
            pstmt.setDouble(3,tariff.getDriverPrice());
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in TariffRepo.addNew method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }

        return success;
    }

    @Override
    public boolean update(Tariff tariff) throws TariffException {
        boolean success = false;
        final String QUERY = "update tariffs set name = ?, rent_price = ?, driver_price = ? where id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, tariff.getName());
            pstmt.setDouble(2, tariff.getRentPrice());
            pstmt.setDouble(3, tariff.getDriverPrice());
            pstmt.setInt(4, tariff.getID());
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in TariffRepo.update method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return success;
    }

    @Override
    public List<Tariff> getListForPagination(int currentPage, int recordsPerPage) throws TariffException {
        final String QUERY = "select id, name,rent_price,driver_price from tariffs limit ?,?";
        List<Tariff> list= new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                list.add(Mapper.createTariffFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in getListForPagination method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return list;
    }

    @Override
    public int getCountInDB() throws TariffException {
        final String QUERY = "select count(id) from tariffs";
        int count = 0;
        Statement stmt = null;
        ResultSet rs;
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in getCountInDB method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, stmt,null);
        }
        return count;
    }

    @Override
    public Optional<Tariff> findById(int Id) throws TariffException {
        final String QUERY = "select id, name, rent_price, driver_price from tariffs where tariffs.id=?";
        Tariff tariff = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,Id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                tariff = Mapper.createTariffFromResultSet(rs);
            }
        } catch (SQLException ex) {
            logger.error("Error in TariffRepo.findById method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(tariff);
    }

    @Override
    public List<Tariff> getAll() throws TariffException {
        final String QUERY = "select id, name,rent_price,driver_price from tariffs";
        List<Tariff> list= new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                list.add(Mapper.createTariffFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in TarifRepo.getAll method", ex);
            throw new TariffException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }
        return list;
    }
}
