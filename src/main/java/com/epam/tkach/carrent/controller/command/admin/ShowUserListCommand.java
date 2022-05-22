package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowUserListCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowUserListCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<String> errorList = new ArrayList();
        errorList.add(Messages.ERROR_PAGE_IN_PROGRESS);
        return Path.PAGE_ERROR_PAGE;
    }
}
