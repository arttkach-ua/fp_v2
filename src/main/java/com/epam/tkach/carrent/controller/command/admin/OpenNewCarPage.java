package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.service.CarBrandService;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OpenNewCarPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewCarPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute(PageParameters.TARIFF_LIST, CarBrandService.getAll());
            request.setAttribute(PageParameters.CAR_BRAND_LIST, TariffService.getAll());
            return Path.PAGE_ADD_CAR;
        } catch (CarBrandRepoException | TariffException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }

    }
}
