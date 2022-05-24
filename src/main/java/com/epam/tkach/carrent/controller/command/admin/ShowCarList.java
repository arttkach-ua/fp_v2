package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCarList implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowCarList.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarRepoI repo = new CarRepoMySql();
        List<Car> carList;
        try {
            int carBrandsCount = repo.getCountInDB();
            int currentPage = 1;
            if (request.getParameter(PageParameters.CURRENT_PAGE) != null) {
                currentPage = Integer.parseInt(request.getParameter(PageParameters.CURRENT_PAGE));
            }
            int recordsPerPage = 5;

            carList = repo.getListForPagination(currentPage, recordsPerPage);
            int nOfPages = carBrandsCount / recordsPerPage;

            if (nOfPages % recordsPerPage > 0) {
                nOfPages++;
            }

            request.setAttribute(PageParameters.CAR_LIST, carList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_CARS;
        } catch (CarRepoException e) {
            logger.error(e);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
