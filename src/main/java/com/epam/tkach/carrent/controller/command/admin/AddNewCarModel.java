package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

public class AddNewCarModel implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCarModel.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errorList = new ArrayList();
        String classParam = request.getParameter(PageParameters.CAR_CLASS);
        String brandIdParam = request.getParameter(PageParameters.CAR_BRAND);
        String modelName = request.getParameter(PageParameters.NAME);
        //Checking for empty model field
        if (modelName.isEmpty()){
            errorList.add(Messages.ERROR_NULL_CAR_MODEL_NAME);
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }

        boolean success = false;

        CarModelRepoI modelRepo = new CarModelRepoMySql();

        CarClass carClass = CarClass.getByID(Integer.parseInt(classParam));
        try {
            success = modelRepo.addNew(Integer.parseInt(brandIdParam),Integer.parseInt(classParam),modelName);
        } catch (CarModelRepoException ex) {
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            logger.error(ex);
        }

        if (success){
            return Path.PAGE_SUCCESS;
        }else{
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
