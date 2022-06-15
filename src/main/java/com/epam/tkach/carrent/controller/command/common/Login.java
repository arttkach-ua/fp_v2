package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.implementation.CryptographyPBKDF;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.service.TransactionService;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login implements ICommand {
    private static final Logger logger = LogManager.getLogger(Login.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        CryptographyI crypto = new CryptographyPBKDF();
        HttpSession session = request.getSession();

        String login = request.getParameter(PageParameters.EMAIL);
        String pass = request.getParameter(PageParameters.PASSWORD);
        try{
            if (!UserService.userIsPresentInDB(login)){
                //User not found
                return Path.prepareErrorPage(request,response, Messages.USER_NOT_FOUND);
            }
            User user = UserService.findByEmail(login);
            //Checking for valid pass
            boolean passIsCorrect = crypto.passIsCorrect(pass, user.getSalt(), user.getPassword());
            if (!passIsCorrect){
                return Path.prepareErrorPage(request,response, Messages.PASS_IS_NOT_CORRECT);
            }
            //User banned
            if (user.getBlocked()){
                return Path.prepareErrorPage(request,response, Messages.ERROR_USER_BLOCKED);
            }
            //Pass is correct.
            session.setAttribute("role", user.getRole());
            session.setAttribute(PageParameters.ID,user.getID());
            if (user.getRole().equals(Role.CLIENT)){
                session.setAttribute(PageParameters.BALANCE, TransactionService.getUserBalance(user.getID()));
            }
            return Path.prepareSuccessPage(request, response, null);

        } catch (UserRepoException | TransactionException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
