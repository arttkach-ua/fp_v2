package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProfile implements ICommand {
    private static final Logger logger = LogManager.getLogger(UpdateProfile.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            logger.debug("Session error");
            return Path.prepareErrorPage(request,response, Messages.ERROR_SESSION_ERROR);
        }
        try {
            int id = (int)session.getAttribute(PageParameters.ID);

            User user = UserService.findByID(id);
            updateUserfromRequest(request, user);
            logger.debug(user.toString());
            UserService.update(user);
            return Path.prepareSuccessPage(request, response, null);

        } catch (UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }

    private void updateUserfromRequest(HttpServletRequest request, User user){
        user.setFirstName(RequestReader.readStringFromRequest(request,PageParameters.FIRST_NAME));
        user.setSecondName(RequestReader.readStringFromRequest(request,PageParameters.SECOND_NAME));
        user.setEmail(RequestReader.readStringFromRequest(request,PageParameters.EMAIL));
        user.setPhone(RequestReader.readStringFromRequest(request,PageParameters.PHONE_NUMBER));
        user.setDocumentInfo(RequestReader.readStringFromRequest(request,PageParameters.DOCUMENT));
        user.setReceiveNotifications(RequestReader.readBooleanFromRequest(request,PageParameters.RECEIVE_NOTIFICATIONS));
    }
}
