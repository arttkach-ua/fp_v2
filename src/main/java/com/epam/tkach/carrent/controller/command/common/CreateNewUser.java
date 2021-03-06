package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.PassGenerationResult;
import com.epam.tkach.carrent.controller.security.implementation.CryptographyPBKDF;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import com.epam.tkach.carrent.model.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class CreateNewUser implements ICommand {
    private static final Logger logger = LogManager.getLogger(CreateNewUser.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserRepoI repo = new UserRepoMySql();
        List<String> errorList = new ArrayList();
        boolean success = false;

        //First step of validation

        User user = Mapper.createUserFromRequest(request);

        try {
            //Checking for existing user
            if (repo.findByEmail(user.getEmail()).isPresent()){
                errorList.add(Messages.USER_EXISTS);
                request.setAttribute(PageParameters.ERRORS, errorList);
                return Path.PAGE_ERROR_PAGE;
            }
            //Validating user card
            boolean isValid = Validator.validateEntity(user,errorList);
            if (!isValid) {
                request.setAttribute(PageParameters.ERRORS, errorList);
                return Path.PAGE_ERROR_PAGE;
            }
            //this is first registration in System
            int countInDd = repo.getCountInDb();
            if (countInDd==0) user.setRole(Role.ADMIN);
            success = repo.addNew(user);
            NotificationService.notifyAboutRegistration(user.getEmail(),SessionHelper.getCurrentLocale(request),user.isReceiveNotifications());
        } catch (UserRepoException e) {
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            logger.error(e);
        }

        if (success){
            return Path.PAGE_SUCCESS;
        }else{
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
