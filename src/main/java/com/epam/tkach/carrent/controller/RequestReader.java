package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.controller.exceptions.*;
import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.PassGenerationResult;
import com.epam.tkach.carrent.controller.security.implementation.CryptographyPBKDF;
import com.epam.tkach.carrent.model.entity.*;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Class used to get values from request and process errors if values are not correct
 * @author Tkach A.
 */
public class RequestReader {
    private static final Logger logger = LogManager.getLogger(RequestReader.class);

    public static int readIntFromRequest(HttpServletRequest request, String param){
        int value = 0;
        try{
            value = Integer.parseInt(request.getParameter(param));
        }catch (NumberFormatException|NullPointerException ex){
            logger.error("Failed to read "+param, ex);
        }
        return value;
    }

    public static double readDoubleFromRequest(HttpServletRequest request, String param){
        double value = 0d;
        try{
            value = Double.parseDouble(request.getParameter(param));
        }catch (NumberFormatException|NullPointerException ex){
            logger.error("Failed to read "+param, ex);
        }
        return value;
    }

    public static boolean readBooleanFromRequest(HttpServletRequest request, String param){
        boolean value = false;
        if (request.getParameter(param)!=null) value = true;
        return value;
    }

    public static String readStringFromRequest(HttpServletRequest request, String param){
        String value = null;
        value = request.getParameter(param);
        return value;
    }

    /**creates car Entity from request
     *
     * @param request - HttpServletRequest
     * @return Car entity
     */
    public static Car createCarFromRequest(HttpServletRequest request){

        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        CarModelRepoI modelRepo = new CarModelRepoMySql();
        CarBrand brand = null;
        CarModel model = null;
        Tariff tariff = null;
        CompleteSet completeSet = null;

        int id = readIntFromRequest(request, PageParameters.ID);
        int brandID = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND);
        int modelID = RequestReader.readIntFromRequest(request, PageParameters.CAR_MODEL);
        int tariffID = RequestReader.readIntFromRequest(request, PageParameters.TARIFF);
        int completeSetID = RequestReader.readIntFromRequest(request, PageParameters.COMPLETE_SET);
        int year = RequestReader.readIntFromRequest(request, PageParameters.YEAR);

        String stateNumber = request.getParameter(PageParameters.STATE_NUMBER);
        String vinCode = request.getParameter(PageParameters.VIN_CODE);


        try {
            brand = brandRepo.findByID(brandID).orElse(null);
            model = modelRepo.findByID(modelID).orElse(null);
            completeSet = CompleteSetService.findById(completeSetID);
            tariff = TariffService.findById(tariffID);

        } catch (CarBrandRepoException | CarModelRepoException | CompleteSetsRepoException | TariffException ex) {
            logger.error(ex);
        }

        Car car = new Car();
        car.setID(id);
        car.setBrand(brand);
        car.setModel(model);
        car.setCarClass(model.getCarClass());
        car.setGraduationYear(year);
        car.setStateNumber(stateNumber);
        car.setVinCode(vinCode);
        car.setCompleteSet(completeSet);
        car.setTariff(tariff);
        return car;
    }

    public static Transaction CreateTopUpTransactionFromRequest(HttpServletRequest request, int id) throws UserRepoException {
        UserRepoI repo = new UserRepoMySql();
        Optional<User> userOpt = repo.findByID(id);
        if (userOpt.isEmpty()) throw new UserRepoException();

        Transaction transaction = new Transaction();
        transaction.setUser(userOpt.get());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("User balance donation");
        transaction.setSum(readDoubleFromRequest(request,PageParameters.SUM));
        logger.debug(transaction.toString());
        return transaction;
    }

}
