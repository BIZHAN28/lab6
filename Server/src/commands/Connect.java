package commands;

import commands.network.CommandDescription;
import commands.network.Response;
import managers.Invoker;
import validation.CommandInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class Connect implements Command{

    @Override
    public Response execute(String[] args, Object obj) throws  IOException {
        Collection<Command> commands =  (Invoker.getCommandMap().values());
        ArrayList<CommandDescription> commandDescriptions = new ArrayList<>();

        for (Command command : commands){
            if (command.getClass().isAnnotationPresent(CommandInfo.class)) {
                CommandInfo commandInfo = command.getClass().getAnnotation(CommandInfo.class);
                commandDescriptions.add(new CommandDescription(commandInfo.name(), commandInfo.argsCount(), commandInfo.requiredObjectType()));
            }
        }
        return new Response("connect", commandDescriptions);
    }
}
