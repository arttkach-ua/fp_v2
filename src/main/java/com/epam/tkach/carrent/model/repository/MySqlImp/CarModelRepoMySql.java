package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarModelRepoMySql implements CarModelRepoI {

    private static final Logger logger = LogManager.getLogger(CarModelRepoMySql.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<CarModel> getCarModelsListForPagination(int currentPage, int recordsPerPage) throws CarModelRepoException {
        final String SELECT_CAR_MODELS_WITH_LIMIT_QUERY = "select id, car_model, car_brands_id, car_classes_id from car_models limit ?,?";
        List<CarModel> carModelList= new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        CarBrandRepoI carBrandRepo = new CarBrandRepoMySql();

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(SELECT_CAR_MODELS_WITH_LIMIT_QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                CarModel carModel = new CarModel(rs.getInt(1), carBrandRepo.findByID(rs.getInt(3)).get() ,rs.getString(2), CarClass.getByID(rs.getInt(4)));
                carModelList.add(carModel);
            }
        } catch (SQLException| CarBrandRepoException ex) {
            logger.error("Error in getCarModelsListForPagination method", ex);
            throw new CarModelRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return carModelList;
    }

    @Override
    public int getCountOfModelsInDB() throws CarModelRepoException {
        final String SELECT_COUNT_OF_CAR_MODELS_QUERY = "select count(id) from car_models";
        int countOfBrands = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs;

        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_COUNT_OF_CAR_MODELS_QUERY);
            while (rs.next()){
                countOfBrands = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in getCountOfModelsInDB method", ex);
            throw new CarModelRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,null);
        }
        return countOfBrands;
    }

    @Override
    public boolean addNew(int carBrandID, int carClassID, String modelName) throws CarModelRepoException {
        final String QUERY = "insert into car_models(car_model, car_brands_id, car_classes_id) values(?,?,?)";
        boolean success = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,modelName);
            pstmt.setInt(2, carBrandID);
            pstmt.setInt(3, carClassID);

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
            throw new CarModelRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return success;
    }

    @Override
    public Optional<CarModel> findByNameAndBrand(int carBrandID, String name) throws CarModelRepoException {

        return Optional.empty();
    }

    @Override
    public List<CarModel> getModelsByBrand(int carBrandID) throws CarModelRepoException{
        final String QUERY = "select id, car_model, car_brands_id, car_classes_id from car_models where car_brands_id = (?)";
        List<CarModel> carModelList= new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,carBrandID);
            rs = pstmt.executeQuery();
            while (rs.next()){
                carModelList.add(getCarModelFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in getModelsByBrand method", ex);
            throw new CarModelRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return carModelList;
    }

    /**
     * Creates a statement of CarModel Class from result of query to CarModels table
     * @param rs
     * columns numbers
     * 1 - ID(int)
     * 2 - carModel(String)
     * 3 - brand ID(int)
     * 4 - class ID(int)
     * @return statement of CarClass
     */
    private CarModel getCarModelFromResultSet(ResultSet rs){
        CarModel model= new CarModel();
        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        try {
            model.setID(rs.getLong(1));
            model.setModelName(rs.getString(2));
            model.setBrand(brandRepo.findByID(rs.getInt(3)).orElse(null));
            model.setCarClass(CarClass.getByID(rs.getInt(4)));
        } catch (SQLException|CarBrandRepoException ex) {
            logger.trace(ex);
        }
        return model;
    }

}
