package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.*;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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

        int id = readIntFromRequest(request, PageParameters.ID);
        int brandID = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND);
        int modelID = RequestReader.readIntFromRequest(request, PageParameters.CAR_MODEL);
        int year = RequestReader.readIntFromRequest(request, PageParameters.YEAR);
        double engine = RequestReader.readDoubleFromRequest(request, PageParameters.ENGINE);

        BodyStyles bodyStyle = BodyStyles.getByID(RequestReader.readIntFromRequest(request, PageParameters.BODY_STYLE));
        TransmissionTypes transmission = TransmissionTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.TRANSMISSION));
        FuelTypes fuel_type = FuelTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.FUEL_TYPE));

        String stateNumber = request.getParameter(PageParameters.STATE_NUMBER);
        String vinCode = request.getParameter(PageParameters.VIN_CODE);
        Double price = RequestReader.readDoubleFromRequest(request, PageParameters.PRICE);

        try {
            brand = brandRepo.findByID(brandID).orElse(null);
            model = modelRepo.findByID(modelID).orElse(null);

        } catch (CarBrandRepoException | CarModelRepoException ex) {
            logger.error(ex);
        }

        Car car = new Car(brand,model,model.getCarClass(),year,bodyStyle,transmission,fuel_type,stateNumber,vinCode, engine, price);
        car.setID(id);
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
