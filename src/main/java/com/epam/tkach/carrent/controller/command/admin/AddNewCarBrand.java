package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.service.CarBrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AddNewCarBrand implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCarBrand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        boolean success = false;
        ArrayList<String> errorList = new ArrayList();
        CarBrand brand = Mapper.createCarBrandFromRequest(request);
        boolean brandIsValid = Validator.validateCarBrand(brand,errorList);

        if (brandIsValid==false) {
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }

        try {
            //checking for existing model
            boolean brandExist = CarBrandService.checkIfExist(RequestReader.readStringFromRequest(request, PageParameters.NAME));

            if (!brandExist) {
                CarBrandService.addNew(brand);
                response.sendRedirect(Path.COMMAND_ALL_CAR_BRANDS);
                return Path.COMMAND_REDIRECT;
            }else {
                return Path.prepareErrorPage(request, response, Messages.ERROR_SUCH_BRAND_EXISTS);
            }
        } catch (CarBrandRepoException | IOException ex) {
            logger.error(ex);
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            return Path.prepareErrorPage(request, response, errorList);
        }
    }
}
