package com.epam.tkach.carrent.controller.command.admin;

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
import java.util.Optional;

public class SetUserBlock implements ICommand {
    private static final Logger logger = LogManager.getLogger(SetUserBlock.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
        boolean newValue = RequestReader.readBooleanFromRequest(request, PageParameters.NEW_VALUE);
        UserRepoI repo = new UserRepoMySql();

        try {
            Optional<User> userOpt = repo.findByID(id);
            if (userOpt.isEmpty()){
                logger.error("Error in SetUserBlock method. User with id".concat(Integer.toString(id)).concat(" not found."));
                return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
            }
            User user = userOpt.get();
            user.setBlocked(newValue);
            logger.debug("new Value:::".concat(Boolean.toString(newValue)));
            repo.update(user);
            return Path.PAGE_SUCCESS;
        } catch (UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
