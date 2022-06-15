package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.service.CarBrandService;
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
            int carBrandFilter = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND_FILTER);
            int statusFilter = RequestReader.readIntFromRequest(request, PageParameters.STATUS_FILTER);
            LinkedHashMap<String, Object> filters = new LinkedHashMap<>();
            int userId = SessionHelper.getUserIdFromSession(request);
            User user = UserService.findByID(userId);
            if (user.getRole()== Role.CLIENT){
                //Нужен отбор по пользователю
                filters.put("client_id", userId);
            }
            if (carBrandFilter != -1&&carBrandFilter != 0){
                filters.put("c.brand_id", carBrandFilter);
                request.setAttribute(PageParameters.CAR_BRAND_FILTER, carBrandFilter);
            }
            if (statusFilter != -1&&statusFilter != 0){
                filters.put("orders.status", statusFilter);
                request.setAttribute(PageParameters.STATUS_FILTER, statusFilter);
            }

            int countInDB = OrderService.getCountInDB(filters);
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;
            List<Order> entityList = OrderService.getList(currentPage,recordsPerPage,filters);
            int nOfPages = PaginationHelper.getNoOfPages(countInDB,recordsPerPage);


            request.setAttribute(PageParameters.CAR_BRAND_LIST, CarBrandService.getAll());
            request.setAttribute(PageParameters.STATUS_LIST, OrderStatuses.values());
            request.setAttribute(PageParameters.ENTITY_LIST, entityList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_ORDERS;
        } catch (AuthenticationException | UserRepoException | OrderRepoException | CarBrandRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}


