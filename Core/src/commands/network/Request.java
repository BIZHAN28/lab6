package commands.network;

import java.io.Serializable;


public class Request implements Serializable {
    private final String commandName;
    private final String[] arguments;
    private final Object object;

    public Request(String commandName, String[] arguments, Object object) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.object = object;
    }


    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public Object getElement() {
        return object;
    }
}
