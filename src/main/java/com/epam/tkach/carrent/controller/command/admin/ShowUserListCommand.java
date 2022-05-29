package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.UserRepoMySql;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowUserListCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowUserListCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserRepoI repo = new UserRepoMySql();
        int currentPage = PaginationHelper.getCurrentPage(request);
        int recordsPerPage = 5;
        int countOfSkips = (currentPage-1)*recordsPerPage;

        try {
            List<User> list= repo.getAll();
            String searchParam = request.getParameter(PageParameters.USER_NAME);

            if (searchParam!=null){//search
                list = list.stream()
                        .filter(e -> e.getFirstName().toLowerCase().contains(searchParam.toLowerCase()) || e.getSecondName().toLowerCase().contains(searchParam.toLowerCase())|| e.getEmail().toLowerCase().contains(searchParam.toLowerCase()))
                        .collect(Collectors.toList());
            }
            int nOfPages = PaginationHelper.getNoOfPages(list.size(), recordsPerPage);

            //pagination. get info for current page
            list = list.stream()
                    .skip(countOfSkips)
                    .limit(recordsPerPage)
                    .collect(Collectors.toList());

            logger.debug("Skips:::" + countOfSkips);
            logger.debug("List size:::" + list.size());
            logger.debug("nOfPages:::" + nOfPages);

            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            request.setAttribute(PageParameters.USERS_LIST, list);
            request.setAttribute(PageParameters.USER_NAME, searchParam);
        } catch (UserRepoException e) {
            logger.error(e);
            List<String> errorList = new ArrayList();
            errorList.add(Messages.ERROR_DATABASE_ERROR);
            request.setAttribute(PageParameters.ERRORS, errorList);
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_ALL_USERS;
    }


}
