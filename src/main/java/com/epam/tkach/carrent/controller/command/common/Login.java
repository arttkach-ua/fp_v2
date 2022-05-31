package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.implementation.CryptographyPBKDF;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Login implements ICommand {
    private static final Logger logger = LogManager.getLogger(Login.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        UserRepoI repo = new UserRepoMySql();
        List<String> messageList = new ArrayList();
        CryptographyI crypto = new CryptographyPBKDF();
        HttpSession session = request.getSession();

        String login = request.getParameter(PageParameters.EMAIL);
        String pass = request.getParameter(PageParameters.PASSWORD);
        try{
            Optional<User> userOpt = repo.findByEmail(login);
            if (userOpt.isEmpty()){
                //User not found
                return Path.prepareErrorPage(request, Messages.USER_NOT_FOUND);
            }
            User user = userOpt.get();
            //Checking for valid pass
            boolean passIsCorrect = crypto.passIsCorrect(pass, user.getSalt(), user.getPassword());
            if (!passIsCorrect){
                return Path.prepareErrorPage(request, Messages.PASS_IS_NOT_CORRECT);
            }
            //User banned
            if (user.getBlocked()){
                return Path.prepareErrorPage(request, Messages.ERROR_USER_BLOCKED);
            }
            //Pass is correct.
            session.setAttribute("role", user.getRole());
            session.setAttribute(PageParameters.ID,user.getID());
//            messageList.add(Messages.LOGIN_SUCCESS);
//            request.setAttribute(PageParameters.ERRORS, messageList);
            return Path.PAGE_SUCCESS;

        } catch (UserRepoException e) {
            logger.error(e);
        }
        return null;
    }
}
