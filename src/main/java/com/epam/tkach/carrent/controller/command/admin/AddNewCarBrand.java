package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

public class AddNewCarBrand implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCarBrand.class);

    CarBrandRepoI repo = new CarBrandRepoMySql();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        boolean success = false;
        ArrayList<String> errorList = new ArrayList();
        CarBrand brand = createCarBrandFromRequest(request);
        boolean brandIsValid = Validator.validateCarBrand(brand,errorList);

        if (brandIsValid==false) {
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }

        try {
            //checking for existing model
            Optional<CarBrand> existingBrand = repo.findByName(request.getParameter(PageParameters.NAME));
            if (existingBrand.isEmpty()) {
                success = repo.addNew(brand);
            }else {
                errorList.add(Messages.ERROR_SUCH_BRAND_EXISTS);
            }
        } catch (CarBrandRepoException ex) {
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            logger.error(ex);
        }

        if (success){
            //redirect to success page
            return Path.PAGE_SUCCESS;
        }else {
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
    }

    private CarBrand createCarBrandFromRequest(HttpServletRequest request) {
        CarBrand brand = new CarBrand();
        brand.setCarBrandName(request.getParameter(PageParameters.NAME));
        return brand;
    }


}
