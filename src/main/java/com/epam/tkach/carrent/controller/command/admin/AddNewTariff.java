package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Mapper;
import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddNewTariff implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewTariff.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        TariffService service = new TariffService();
        ArrayList<String> errorList = new ArrayList();
        try{
            Tariff tariff = Mapper.createTariffFromRequest(request);
            boolean isValid = Validator.validateEntity(tariff,errorList);

            if (!isValid) return Path.prepareErrorPage(request, response, errorList);

            service.addNew(tariff);
            return Path.prepareSuccessPage(request,response,null);
        }
        catch (TariffException ex){
            logger.error(ex);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
