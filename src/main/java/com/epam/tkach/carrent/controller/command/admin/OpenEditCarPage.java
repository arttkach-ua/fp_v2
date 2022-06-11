package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.service.CarBrandService;
import com.epam.tkach.carrent.model.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenEditCarPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenEditCarPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
            Car car = CarService.getById(id);
            Mapper.setCarFieldsToRequest(request, car);

            request.setAttribute(PageParameters.CAR_BRAND_LIST, CarBrandService.getAll());
            request.setAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            request.setAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Path.PAGE_EDIT_CAR;
        } catch (CarRepoException | CarBrandRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
