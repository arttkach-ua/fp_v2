package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateTariff implements ICommand {
    private static final Logger logger = LogManager.getLogger(UpdateTariff.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Tariff tariff = Mapper.createTariffFromRequest(request);
        try {
            logger.debug(tariff.toString());
            TariffService.update(tariff);
            return Path.prepareSuccessPage(request, response, null);
        } catch (TariffException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
