package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.repository.MySqlImp.OrderRepoMySql;
import com.epam.tkach.carrent.model.service.OrderService;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

public class ShowOrders implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowOrders.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            LinkedHashMap<String, Object> filters = new LinkedHashMap<>();
            int userId = RequestReader.getUserIdFromSession(request);
            User user = UserService.findByID(userId);
            if (user.getRole()== Role.CLIENT){
                //Нужен отбор по пользователю
                filters.put("client_id", userId);
            }

            int countInDB = OrderService.getCountInDB(filters);
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;
            List<Order> entityList = OrderService.getList(currentPage,recordsPerPage,filters);
            int nOfPages = PaginationHelper.getNoOfPages(countInDB,recordsPerPage);


            request.setAttribute(PageParameters.ENTITY_LIST, entityList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_ORDERS;
        } catch (AuthenticationException | UserRepoException | OrderRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}


