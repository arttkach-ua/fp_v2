package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.exceptions.*;
import com.epam.tkach.carrent.model.QueryBuilder;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CarRepoMySql implements CarRepoI {

    private static final Logger logger = LogManager.getLogger(CarRepoMySql.class);

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(Car car) throws CarRepoException {
        final String QUERY = "insert into cars(brand_id, model_id, graduation_year, vin_code, state_number,tariff_id,complete_set_id) VALUES (?,?,?,?,?,?,?)";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,car.getBrand().getID());
            pstmt.setInt(2,car.getModel().getID());
            pstmt.setInt(3,car.getGraduationYear());
            pstmt.setString(4,car.getVinCode());
            pstmt.setString(5,car.getStateNumber());
            pstmt.setDouble(6, car.getTariff().getID());
            pstmt.setInt(7, car.getCompleteSet().getID());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in CarRepo.addNew car method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }

        return success;
    }

    @Override
    public List<Car> getListForPagination(int currentPage, int recordsPerPage) throws CarRepoException {
        final String QUERY = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id, available from cars limit ?,?";
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
                carList.add(Mapper.createCarFromResultSet(rs));
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
    public List<Car> getListForPagination(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws CarRepoException {
        String query_ = "select cars.id,\n" +
                "        cars.graduation_year,\n" +
                "        cars.model_id,\n" +
                "        cars.brand_id,\n" +
                "        cars.vin_code,\n" +
                "        cars.state_number,\n" +
                "        cars.tariff_id,\n" +
                "        cars.complete_set_id,\n" +
                "        cs.transmission_id,\n" +
                "        cs.body_style_id,\n" +
                "        cs.engine,\n" +
                "        cs.fuel_type_id,\n" +
                "        cm.car_classes_id,\n" +
                "        t.rent_price,\n" +
                "        cb.brand,\n" +
                "         cars.available from cars\n" +
                " LEFT JOIN complete_sets cs on cars.complete_set_id = cs.id\n" +
                " LEFT JOIN car_models cm on cars.model_id = cm.id\n" +
                " LEFT JOIN tariffs t on cars.tariff_id = t.id\n" +
                " LEFT JOIN car_brands cb on cm.car_brands_id = cb.id";

        List<Car> entityList= new ArrayList();
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
                entityList.add(Mapper.createCarFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in CarRepo.getListForPaginationWithFilter method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return entityList;
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

    @Override
    public int getCountInDB(LinkedHashMap<String, Object> filters) throws CarRepoException {
        String query_ = "select count(cars.id) from cars\n" +
                "    LEFT JOIN complete_sets cs on cars.complete_set_id = cs.id\n" +
                "                LEFT JOIN car_models cm on cars.model_id = cm.id";
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
            logger.error("Error in CarReoiMySql.getCountInDB method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return count;
    }

    @Override
    public Optional<Car> getById(int id) throws CarRepoException {
        final String QUERY = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id, available from cars where id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Car car = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                car = Mapper.createCarFromResultSet(rs);
                break;
            }
        } catch (SQLException ex) {
            logger.error("Error in CarRepoMySQL.getById method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public boolean update(Car car) throws CarRepoException {
        final String QUERY = "update cars set brand_id = ?, model_id = ?, graduation_year = ?, vin_code = ?, state_number = ?,tariff_id = ?, complete_set_id = ?, available=? where id=?";
        boolean success = false;
        PreparedStatement pstmt = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,car.getBrand().getID());
            pstmt.setInt(2,car.getModel().getID());
            pstmt.setInt(3,car.getGraduationYear());
            pstmt.setString(4,car.getVinCode());
            pstmt.setString(5,car.getStateNumber());
            pstmt.setDouble(6, car.getTariff().getID());
            pstmt.setInt(7, car.getCompleteSet().getID());
            pstmt.setBoolean(8, car.isAvailable());
            pstmt.setInt(9, car.getID());

            logger.debug("update:::" + car.isAvailable());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in carRepoMySql.update method", ex);
            throw new CarRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }

        return success;
    }
}
