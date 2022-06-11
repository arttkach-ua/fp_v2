package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SetUserBlock implements ICommand {
    private static final Logger logger = LogManager.getLogger(SetUserBlock.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
        String param = RequestReader.readStringFromRequest(request, PageParameters.NEW_VALUE);
        boolean newValue = "true".equals(param);
        try {
            UserService.setBlockToUser(id, newValue);
            return Path.prepareSuccessPage(request, response, null);
        } catch (UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
