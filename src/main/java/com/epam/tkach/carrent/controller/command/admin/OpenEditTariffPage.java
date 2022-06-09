package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

public class OpenEditTariffPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenEditTariffPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = RequestReader.readIntFromRequest(request, PageParameters.ID);

        try {
            request.setAttribute(PageParameters.TARIFF, TariffService.findById(id));
            return Path.PAGE_EDIT_TARIFF;
        } catch (TariffException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
