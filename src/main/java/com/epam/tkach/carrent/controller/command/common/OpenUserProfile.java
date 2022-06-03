package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
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

public class OpenUserProfile implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenUserProfile.class);
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
            setUserPropertiesToRequest(request, user);
            return Path.PAGE_MY_PROGILE;

        } catch (UserRepoException e) {
            logger.error(e);
        }

        return null;
    }

    private void setUserPropertiesToRequest(HttpServletRequest request, User user){
        request.setAttribute(PageParameters.ID, user.getID());
        request.setAttribute(PageParameters.EMAIL, user.getEmail());
        request.setAttribute(PageParameters.FIRST_NAME, user.getFirstName());
        request.setAttribute(PageParameters.SECOND_NAME, user.getSecondName());
        request.setAttribute(PageParameters.PHONE_NUMBER, user.getPhone());
        request.setAttribute(PageParameters.DOCUMENT, user.getDocumentInfo());
        request.setAttribute(PageParameters.ROLE, user.getRole());
        request.setAttribute(PageParameters.RECEIVE_NOTIFICATIONS, user.isReceiveNotifications());
    }
}

