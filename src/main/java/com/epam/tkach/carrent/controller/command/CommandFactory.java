package com.epam.tkach.carrent.controller.command;

import com.epam.tkach.carrent.controller.command.admin.*;
import com.epam.tkach.carrent.controller.command.admin.CompleteSets.*;
import com.epam.tkach.carrent.controller.command.client.*;
import com.epam.tkach.carrent.controller.command.common.*;
import com.epam.tkach.carrent.controller.command.manager.*;

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
        commands.put("myProfile", new OpenUserProfile());
        commands.put("updateProfile",new UpdateProfile());

        //admin commands
        commands.put("usersList", new ShowUserListCommand());
        commands.put("carBrands", new ShowCarBrands());
        commands.put("carModels", new ShowCarModels());
        commands.put("tariffsList", new ShowTariffList());
        commands.put("openNewCarBrandPage", new OpenNewCarBrandPage());
        commands.put("openNewCarModelPage", new OpenNewCarModelPage());
        commands.put("openNewCarPage", new OpenNewCarPage());
        commands.put("addNewCarBrand", new AddNewCarBrand());
        commands.put("addNewCarModel", new AddNewCarModel());
        commands.put("openNewTariffPage", new OpenNewTariffPage());
        commands.put("addNewCar", new AddNewCar());
        commands.put("addNewTariff", new AddNewTariff());
        commands.put("setUserBlock", new SetUserBlock());
        commands.put("editCar", new OpenEditCarPage());
        commands.put("updateCar", new UpdateCar());
        commands.put("editTariff", new OpenEditTariffPage());
        commands.put("updateTariff", new UpdateTariff());
        commands.put("completeSets", new ShowCompleteSets());
        commands.put("openNewCompleteSetPage", new OpenNewCompleteSetPage());
        commands.put("addNewCompleteSet", new AddNewCompleteSet());
        commands.put("editCompleteSet", new OpenEditCompleteSetPage());
        commands.put("updateCompleteSet", new UpdateCompleteSet());

        //commands.put("addNewUser", new OpenRegisterForm());

        //manager commands
        commands.put("ConfirmOrder", new ConfirmOrder());
        commands.put("OpenDeclineOrderPage", new OpenDeclineOrderPage());
        commands.put("declineOrder", new DeclineOrder());
        commands.put("closeOrder", new CloseOrder());
        commands.put("closeOrderWithDamege", new CloseOrderWithDamage());
        commands.put("openErrorPage", new OpenErrorPage());
        commands.put("openSuccessPage", new OpenSuccessPage());
        commands.put("closeOrder", new CloseOrder());
        commands.put("closeOrderWithDamage", new CloseOrderWithDamage());
        commands.put("openCloseOrderWithDamage", new OpenCloseOrderPageWithDamage());

        //client commands
        //commands.put("showUserOrders", new ShowUserListCommand());
        commands.put("openTopUpPage", new OpenTopUpPage());
        commands.put("TopUp", new TopUp());
        commands.put("selectCar", new OpenCarSelectPage());
        commands.put("OpenNewOrderPage", new OpenNewOrderPage());
        commands.put("addNewOrder", new AddNewOrder());
        commands.put("ordersList", new ShowOrders());
        commands.put("invoiceList", new ShowInvoices());
        commands.put("payInvoice", new PayInvoice());

    }

    public ICommand getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
