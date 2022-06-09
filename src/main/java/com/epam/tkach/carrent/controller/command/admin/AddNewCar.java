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
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddNewCar implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCar.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        boolean success = false;
        ArrayList<String> errorList = new ArrayList();
        Car car = RequestReader.createCarFromRequest(request);
        boolean isValid = Validator.validateEntity(car,errorList);
        if (isValid==false) {
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }

        try {
            CarRepoI carRepo = RepositoryFactory.getCarRepo();
            success = carRepo.addNew(car);
        } catch (CarRepoException ex) {
            success = false;
            logger.error("Error while adding new car",ex);
        }
        if (success){
            return Path.PAGE_SUCCESS;//temp
        }else{
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }


}
