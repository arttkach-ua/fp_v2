package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCarModels implements ICommand {

    private static final Logger logger = LogManager.getLogger(ShowCarModels.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            CarModelRepoI repo = new CarModelRepoMySql();
            List<CarModel> carModelsList;
            int countInDB = repo.getCountOfModelsInDB();
            int currentPage = 1;
            if (request.getParameter(PageParameters.CURRENT_PAGE) != null) {
                currentPage = Integer.parseInt(request.getParameter(PageParameters.CURRENT_PAGE));
            }
            int recordsPerPage = 5;

            carModelsList = repo.getCarModelsListForPagination(currentPage, recordsPerPage);

            int nOfPages = countInDB / recordsPerPage;

            //!!!!Refactor this
            if (nOfPages % recordsPerPage > 0) {
                nOfPages++;
            }

            request.setAttribute("carModelsList", carModelsList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_CAR_MODELS;
        } catch (CarModelRepoException ex) {
            logger.error(ex);
            return Path.PAGE_ERROR_PAGE;
        }

    }
}