package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowCarBrands implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowCarBrands.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        CarBrandRepoI repo = new CarBrandRepoMySql();
        List<CarBrand> carBrandsList;
        try {
            int carBrandsCount = repo.getCountOfBrandsInDB();
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;

            carBrandsList = repo.getCarBrandsForPagination(currentPage, recordsPerPage);
            int nOfPages = PaginationHelper.getNoOfPages(carBrandsCount, recordsPerPage);

            request.setAttribute("carBrandsList", carBrandsList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_CAR_BRANDS;
        } catch (CarBrandRepoException e) {
            logger.error(e);
            List<String> errorList = new ArrayList<>();
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
