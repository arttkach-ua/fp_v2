package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UpdateProfile implements ICommand {
    private static final Logger logger = LogManager.getLogger(UpdateProfile.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        UserRepoI repo = new UserRepoMySql();

        if (session == null) {
            logger.debug("Session error");
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
        try {
            int id = (int)session.getAttribute(PageParameters.ID);
            Optional<User> userOpt = repo.findByID(id);
            if (userOpt.isEmpty()){
                logger.debug("Didnt get user from db, id=" + id);
                return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
            }
            User user = userOpt.get();
            updateUserfromRequest(request, user);
            logger.debug(user.toString());
            repo.update(user);
            return Path.PAGE_SUCCESS;

        } catch (UserRepoException e) {
            logger.error(e);
        }

        return null;
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
