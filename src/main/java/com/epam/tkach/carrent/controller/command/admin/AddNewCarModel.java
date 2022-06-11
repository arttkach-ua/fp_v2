package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.service.CarModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewCarModel implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCarModel.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int classId = RequestReader.readIntFromRequest(request, PageParameters.CAR_CLASS);
        int brandId = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND);
        String modelName = RequestReader.readStringFromRequest(request, PageParameters.NAME);
        //Checking for empty model field
        if (modelName.isEmpty()){
            return Path.prepareErrorPage(request, response, Messages.ERROR_NULL_CAR_MODEL_NAME);
        }

        try {
            CarModelService.addNew(brandId,modelName,classId);
            return Path.prepareSuccessPage(request, response, null);
        } catch (CarModelRepoException ex) {
            logger.error(ex);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
