package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.controller.exceptions.*;
import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.PassGenerationResult;
import com.epam.tkach.carrent.controller.security.implementation.CryptographyPBKDF;
import com.epam.tkach.carrent.model.entity.*;
import com.epam.tkach.carrent.model.entity.enums.*;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
    private static final Logger logger = LogManager.getLogger(Mapper.class);

    public static Tariff createTariffFromResultSet(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setID(rs.getInt(1));
        tariff.setName(rs.getString(2));
        tariff.setRentPrice(rs.getDouble(3));
        tariff.setDriverPrice(rs.getDouble(4));
        return tariff;
    }

    public static Tariff createTariffFromRequest(HttpServletRequest request){
        Tariff tariff = new Tariff();
        tariff.setID(RequestReader.readIntFromRequest(request, PageParameters.ID));
        tariff.setName(RequestReader.readStringFromRequest(request, PageParameters.NAME));
        tariff.setRentPrice(RequestReader.readDoubleFromRequest(request, PageParameters.RENT_PRICE));
        tariff.setDriverPrice(RequestReader.readDoubleFromRequest(request, PageParameters.DRIVER_PRICE));
        return tariff;
    }

    public static User createUserFromRequest(HttpServletRequest request){
        CryptographyI crypto = new CryptographyPBKDF();
        String pass = request.getParameter(PageParameters.PASSWORD);
        String role = request.getParameter(PageParameters.ROLE);
        PassGenerationResult passGen;

        logger.debug("pass:::" + pass);

        User user = new User();
        user.setEmail(request.getParameter(PageParameters.EMAIL));
        user.setFirstName(request.getParameter(PageParameters.FIRST_NAME));
        user.setSecondName(request.getParameter(PageParameters.SECOND_NAME));
        user.setPhone(request.getParameter(PageParameters.PHONE_NUMBER));
        user.setDocumentInfo(request.getParameter(PageParameters.DOCUMENT));
        user.setReceiveNotifications(RequestReader.readBooleanFromRequest(request,PageParameters.RECEIVE_NOTIFICATIONS));
        user.setRole(role==null? Role.CLIENT:Role.getByID(Integer.parseInt(role)));
        try {
            if (pass != null) {
                passGen = crypto.generateStrongPasswordHash(pass,null);
                user.setPassword(passGen.getGeneratedPassHash());
                user.setSalt(passGen.getSalt());
                logger.debug("generated:::"+user.getPassword());
            }
        }catch (NoSuchAlgorithmException | InvalidKeySpecException ex){
            logger.error(ex);
        }
        return user;
    }

    public static CarBrand createCarBrandFromRequest(HttpServletRequest request) {
        CarBrand brand = new CarBrand();
        brand.setCarBrandName(request.getParameter(PageParameters.NAME));
        return brand;
    }

    public static CompleteSet createCompleteSetFromRequest(HttpServletRequest request) throws CarModelRepoException {
        CompleteSet completeSet  = new CompleteSet();
        completeSet.setID(RequestReader.readIntFromRequest(request, PageParameters.ID));
        completeSet.setCarModel(CarModelService.findByID(RequestReader.readIntFromRequest(request, PageParameters.CAR_MODEL)));
        completeSet.setBodyStyle(BodyStyles.getByID(RequestReader.readIntFromRequest(request, PageParameters.BODY_STYLE)));
        completeSet.setTransmission(TransmissionTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.TRANSMISSION)));
        completeSet.setFuelType(FuelTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.FUEL_TYPE)));
        completeSet.setEngine(RequestReader.readDoubleFromRequest(request, PageParameters.ENGINE));
        completeSet.generateName();
        return completeSet;
    }

    public static CompleteSet createCompleteSetFromResultSet(ResultSet rs) throws SQLException, CarModelRepoException {
        CompleteSet completeSet  = new CompleteSet();
        completeSet.setID(rs.getInt(1));
        completeSet.setName(rs.getString(2));
        completeSet.setCarModel(CarModelService.findByID(rs.getInt(3)));
        completeSet.setBodyStyle(BodyStyles.getByID(rs.getInt(4)));
        completeSet.setTransmission(TransmissionTypes.getByID(rs.getInt(5)));
        completeSet.setFuelType(FuelTypes.getByID(rs.getInt(6)));
        completeSet.setEngine(rs.getDouble(7));

        return completeSet;
    }

    public static Order createOrderFromResultSet(ResultSet rs) throws SQLException, CarRepoException, UserRepoException {
        Order order = new Order();
        order.setID(rs.getInt("id"));
        order.setClient(UserService.findByID(rs.getInt("client_id")));
        order.setCar(CarService.getById(rs.getInt("car_id")));
        order.setManager(UserService.findByID(rs.getInt("manager_id")));
        order.setDocuments(rs.getString("docements"));
        order.setDaysCount(rs.getInt("day_counts"));
        order.setPrice(rs.getDouble("price"));
        order.setStatus(OrderStatuses.getByID(rs.getInt("status")));
        order.setWithDriver(rs.getBoolean("with_driver"));
        order.setManagerComment(rs.getString("manager_comment"));
        order.setDriverPrice(rs.getDouble("driver_price"));
        order.setRentSum(rs.getDouble("rent_sum"));

        return order;
    }

    public static Car createCarFromResultSet(ResultSet rs){
        Car car = null;
        CarModelRepoI modelRepo = new CarModelRepoMySql();
        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        try {
            car = new Car();
            car.setID(rs.getInt(1));
            car.setGraduationYear(rs.getInt(2));
            car.setModel(modelRepo.findByID(rs.getInt("model_id")).orElse(null));
            car.setBrand(brandRepo.findByID(rs.getInt("brand_id")).orElse(null));
            car.setStateNumber(rs.getString("state_number"));
            car.setVinCode(rs.getString("vin_code"));
            car.setTariff(TariffService.findById(rs.getInt("tariff_id")));
            car.setCarClass(car.getModel().getCarClass());
            car.setCompleteSet(CompleteSetService.findById(rs.getInt("complete_set_id")));
            car.setAvailable(rs.getBoolean("available"));
            logger.debug(rs.getBoolean("available"));
        } catch (SQLException | CarModelRepoException | CarBrandRepoException | TariffException | CompleteSetsRepoException ex) {
            logger.error(ex);
        }
        return car;
    }

    public static Invoice createInvoiceFromResultSet(ResultSet rs) throws SQLException, UserRepoException, OrderRepoException {
        Invoice invoice = new Invoice();
        invoice.setID(rs.getInt("id"));
        invoice.setClient(UserService.findByID(rs.getInt("client_id")));
        invoice.setAmount(rs.getDouble("amount"));
        invoice.setDescription(rs.getString("description"));
        invoice.setType(InvoiceTypes.getByID(rs.getInt("type")));
        invoice.setOrder(OrderService.findById(rs.getInt("order_id")));
        invoice.setPaid(rs.getBoolean("paid"));
        invoice.setDateTime(rs.getTimestamp("timestamp"));
        return invoice;
    }

    public static void setCarFieldsToRequest(HttpServletRequest request, Car car){
        request.setAttribute(PageParameters.ID, car.getID());
        request.setAttribute(PageParameters.CAR_BRAND, car.getBrand());
        request.setAttribute(PageParameters.CAR_MODEL, car.getModel());
        request.setAttribute(PageParameters.YEAR, car.getGraduationYear());
        request.setAttribute(PageParameters.VIN_CODE, car.getVinCode());
        request.setAttribute(PageParameters.STATE_NUMBER, car.getStateNumber());
    }

}
