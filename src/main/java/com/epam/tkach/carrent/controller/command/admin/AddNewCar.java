package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddNewCar implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCar.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarRepoI carRepo = new CarRepoMySql();
        boolean success = false;
        ArrayList<String> errorList = new ArrayList();
        Car car = createCarFromRequest(request);
        boolean isValid = Validator.validateEntity(car,errorList);
        if (isValid==false) {
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }

        try {
            success = carRepo.addNew(car);
        } catch (CarRepoException ex) {
            success = false;
            logger.error("Error while adding new car",ex);
        }
        if (success){
            return Path.PAGE_SUCCESS;//temp
        }else{
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
    }

    private Car createCarFromRequest(HttpServletRequest request){

        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        CarModelRepoI modelRepo = new CarModelRepoMySql();
        CarBrand brand = null;
        CarModel model = null;
        int year = 0;
        double engine = 0d;

        int brandID = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND);
        int modelID = RequestReader.readIntFromRequest(request, PageParameters.CAR_MODEL);
        year = RequestReader.readIntFromRequest(request, PageParameters.YEAR);
        engine = RequestReader.readDoubleFromRequest(request, PageParameters.ENGINE);

        BodyStyles bodyStyle = BodyStyles.getByID(RequestReader.readIntFromRequest(request, PageParameters.BODY_STYLE));
        TransmissionTypes transmission = TransmissionTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.TRANSMISSION));
        FuelTypes fuel_type = FuelTypes.getByID(RequestReader.readIntFromRequest(request, PageParameters.FUEL_TYPE));

        String stateNumber = request.getParameter(PageParameters.STATE_NUMBER);
        String vinCode = request.getParameter(PageParameters.VIN_CODE);
        Double price = RequestReader.readDoubleFromRequest(request, PageParameters.PRICE);

        try {
            brand = brandRepo.findByID(brandID).orElse(null);
            model = modelRepo.findByID(modelID).orElse(null);

        } catch (CarBrandRepoException|CarModelRepoException ex) {
            logger.error(ex);
        }

        Car car = new Car(brand,model,model.getCarClass(),year,bodyStyle,transmission,fuel_type,stateNumber,vinCode, engine, price);
        return car;
    }


}
