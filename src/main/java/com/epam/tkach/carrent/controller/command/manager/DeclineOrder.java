package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineOrder implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
