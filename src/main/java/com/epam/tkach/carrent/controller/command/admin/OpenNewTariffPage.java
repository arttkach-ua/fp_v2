package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenNewTariffPage implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.PAGE_ADD_TARIFF;
    }
}
