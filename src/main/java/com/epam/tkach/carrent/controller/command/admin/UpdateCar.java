package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCar implements ICommand {
    private static final Logger logger = LogManager.getLogger(UpdateCar.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarRepoI repo = new CarRepoMySql();
        Car car = RequestReader.createCarFromRequest(request);
        try {
            repo.update(car);
            return Path.PAGE_SUCCESS;
        } catch (CarRepoException e) {
           logger.error(e);
        }
        return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
    }
}
