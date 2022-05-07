package controller.command;

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
        //commands.put("login", new LoginCommand());
        //commands.put("logout", new LogoutCommand());
        //commands.put("setLocale", new setLocale());
        commands.put("redirect", null);
        //admin commands
        //commands.put("allUsers", new ShowUserListCommand());
        //manager commands

        //client commands
        //commands.put("showUserOrders", new ShowUserListCommand());

    }

    public ICommand getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }

}
