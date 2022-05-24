package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepoMySql implements CarRepoI {

    private static final Logger logger = LogManager.getLogger(CarRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Car car) throws CarRepoException {
        final String QUERY = "insert into cars(brand_id, model_id, graduation_year, body_style_id, transmission_id, fuel_type_id, engine, vin_code, state_number) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,car.getBrand().getID());
            pstmt.setInt(2,car.getModel().getID());
            pstmt.setInt(3,car.getGraduationYear());
            pstmt.setInt(4,car.getBodyStyle().getValue());
            pstmt.setInt(5,car.getTransmission().getValue());
            pstmt.setInt(6,car.getFuelType().getValue());
            pstmt.setDouble(7,car.getEngine());
            pstmt.setString(8,car.getVinCode());
            pstmt.setString(9,car.getStateNumber());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew car method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }

        return success;
    }

    @Override
    public List<Car> getListForPagination(int currentPage, int recordsPerPage) throws CarRepoException {
        final String QUERY = "select id, graduation_year, body_style_id, transmission_id, fuel_type_id, engine, model_id, brand_id, vin_code, state_number from cars limit ?,?";
        List<Car> carList= new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                carList.add(createCarFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in addUser method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return carList;
    }

    @Override
    public int getCountInDB() throws CarRepoException {
        final String QUERY = "select count(id) from cars";
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
            logger.error("Error in getCountInDB method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }
        return count;
    }

    private Car createCarFromResultSet(ResultSet rs){
        Car car = null;
        CarModelRepoI modelRepo = new CarModelRepoMySql();
        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        try {
            car = new Car();
            car.setID(rs.getInt(1));
            car.setGraduationYear(rs.getInt(2));
            car.setBodyStyle(BodyStyles.getByID(rs.getInt(3)));
            car.setTransmission(TransmissionTypes.getByID(rs.getInt("transmission_id")));
            car.setFuelType(FuelTypes.getByID(rs.getInt(5)));
            car.setEngine(rs.getDouble(6));
            car.setModel(modelRepo.findByID(rs.getInt("model_id")).orElse(null));
            car.setBrand(brandRepo.findByID(rs.getInt("brand_id")).orElse(null));
            car.setStateNumber(rs.getString("state_number"));
            car.setVinCode(rs.getString("vin_code"));
            car.setCarClass(car.getModel().getCarClass());
        } catch (SQLException | CarModelRepoException | CarBrandRepoException ex) {
            logger.error(ex);
        }
        return car;
    }
}
