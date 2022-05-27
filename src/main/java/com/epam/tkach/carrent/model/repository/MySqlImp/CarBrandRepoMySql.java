package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarBrandRepoMySql implements CarBrandRepoI {
    private static final Logger logger = LogManager.getLogger(CarBrandRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public List<CarBrand> getCarBrandsForPagination(int currentPage, int recordsPerPage) throws CarBrandRepoException {
        final String SELECT_CAR_BRANDS_WITH_LIMIT_QUERY = "select id, brand from car_brands limit ?,?";
        List<CarBrand> carBrandList= new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(SELECT_CAR_BRANDS_WITH_LIMIT_QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                CarBrand carBrand = new CarBrand(rs.getInt(1), rs.getString(2));
                carBrandList.add(carBrand);
            }
        } catch (SQLException ex) {
            logger.error("Error in addUser method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return carBrandList;
    }

    @Override
    public int getCountOfBrandsInDB() throws CarBrandRepoException {
        final String SELECT_COUNT_OF_CAR_BRANDS_QUERY = "select count(id) from car_brands";
        int countOfBrands = 0;
        Statement stmt = null;
        ResultSet rs;
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_COUNT_OF_CAR_BRANDS_QUERY);
            while (rs.next()){
                countOfBrands = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in addUser method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,null);
        }
        return countOfBrands;
    }

    @Override
    public Optional<CarBrand> findByID(int id) throws CarBrandRepoException {
        if (id<0) return Optional.empty();

        final String QUERY = "select id,brand from car_brands where id=?";
        CarBrand carBrand = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                carBrand = new CarBrand(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            logger.error("Error in addUser method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.of(carBrand);
    }

    @Override
    public boolean addNew(CarBrand carBrand) throws CarBrandRepoException {
        final String QUERY = "insert into car_brands(brand) values(?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,carBrand.getCarBrandName());
            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }

        return success;
    }

    @Override
    public Optional<CarBrand> findByName(String name) throws CarBrandRepoException {
        final String QUERY = "select car_brands.id, car_brands.brand from car_brands where car_brands.brand=?";
        CarBrand carBrand = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,name);
            rs = pstmt.executeQuery();
            while (rs.next()){
                carBrand = new CarBrand(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            logger.error("Error in addUser method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(carBrand);
    }

    @Override
    public List<CarBrand> getAll() throws CarBrandRepoException {
        final String QUERY = "select id,brand from car_brands order by brand";
        ArrayList<CarBrand> list= new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                list.add(new CarBrand(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            logger.error("Error in getAll method", ex);
            throw new CarBrandRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }

        return list;
    }
}
