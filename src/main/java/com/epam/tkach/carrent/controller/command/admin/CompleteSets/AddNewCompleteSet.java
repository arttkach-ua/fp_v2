package com.epam.tkach.carrent.controller.command.admin.CompleteSets;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddNewCompleteSet implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCompleteSet.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            CompleteSet completeSet = Mapper.createCompleteSetFromRequest(request);

            ArrayList<String> errorList = new ArrayList();
            boolean brandIsValid = Validator.validateEntity(completeSet,errorList);
            if (brandIsValid==false) return Path.prepareErrorPage(request, errorList);

            CompleteSetService.addNew(completeSet);
            return Path.PAGE_SUCCESS;
        } catch (CompleteSetsRepoException | CarModelRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
