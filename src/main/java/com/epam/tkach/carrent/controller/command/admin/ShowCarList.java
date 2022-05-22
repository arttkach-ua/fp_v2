package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCarList implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowCarList.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        List<CarBrand> brendList = null;
        int selectedBrand = 0;

        try {
            brendList = brandRepo.getAll();
        } catch (CarBrandRepoException ex) {
            logger.error(ex);
        }
        if (request.getParameter("brand_selection")!=null){
            selectedBrand = Integer.parseInt(request.getParameter("brand_selection"));
        };


        request.setAttribute("selectedBrand",selectedBrand);
        System.out.println(selectedBrand);
        request.setAttribute("carBrands",brendList);
        return Path.PAGE_ALL_CARS;
    }
}
