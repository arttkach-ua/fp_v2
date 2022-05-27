package com.epam.tkach.carrent.controller.command;

import com.epam.tkach.carrent.controller.command.admin.*;
import com.epam.tkach.carrent.controller.command.common.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for command factory.
 * Used Singleton
 * @author Tkach Artem
 */
public class CommandFactory{
    private static CommandFactory instance = new CommandFactory();
    private static Map<String, ICommand> commands = new HashMap<>();

    private CommandFactory() {}

    public static synchronized CommandFactory commandFactory() {
        if (instance != null) {
            return instance;
        }
        synchronized (CommandFactory.class) {
            if (instance == null) {
                instance = new CommandFactory();
            }
        }
        return instance;
    }

    /**
     * Here go all command that used in web application
     */
    static {
        //all users access commands
        commands.put("openLoginForm", new OpenLoginForm());
        commands.put("openRegisterForm", new OpenRegisterForm());
        commands.put("login", new Login());
        commands.put("logout", new LogOut());
        commands.put("register", new CreateNewUser());
        commands.put("i18n", new I18N());
        commands.put("redirect", null);
        commands.put("carList", new ShowCarList());

        //admin commands
        //commands.put("allUsers", new ShowUserListCommand());
        commands.put("usersList", new ShowUserListCommand());
        commands.put("carBrands", new ShowCarBrands());
        commands.put("carModels", new ShowCarModels());
        commands.put("openNewCarBrandPage", new OpenNewCarBrandPage());
        commands.put("openNewCarModelPage", new OpenNewCarModelPage());
        commands.put("openNewCarPage", new OpenNewCarPage());
        commands.put("addNewCarBrand", new AddNewCarBrand());
        commands.put("addNewCarModel", new AddNewCarModel());
        commands.put("addNewCar", new AddNewCar());

        //manager commands

        //client commands
        //commands.put("showUserOrders", new ShowUserListCommand());

    }

    public ICommand getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
