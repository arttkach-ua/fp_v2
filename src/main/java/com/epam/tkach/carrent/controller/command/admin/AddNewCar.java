package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddNewCar implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCar.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        ArrayList<String> errorList = new ArrayList();
        Car car = RequestReader.createCarFromRequest(request);
        boolean isValid = Validator.validateEntity(car,errorList);
        if (isValid==false) {
            return Path.prepareErrorPage(request, response, errorList);
        }
        try {
            CarService.addNew(car);
            return Path.prepareSuccessPage(request,response,null);
        } catch (CarRepoException ex) {
            logger.error("Error while adding new car",ex);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
