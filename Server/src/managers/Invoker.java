package managers;

import commands.*;
import commands.network.Response;


import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;

public class Invoker {
    private static final HashMap<String, Command> commandMap = new HashMap<>();
    private Receiver receiver;
    public Invoker(CollectionManager collectionManager, ScriptStack scriptStack){
        this.receiver = new Receiver(collectionManager, scriptStack);

        commandMap.put("help", new Help(receiver));
        commandMap.put("info", new Info(receiver));
        commandMap.put("show", new Show(receiver));
        commandMap.put("add", new Add(receiver));
        commandMap.put("update", new Update(receiver));
        commandMap.put("remove_by_id", new RemoveById(receiver));
        commandMap.put("clear", new Clear(receiver));
        commandMap.put("save", new Save(receiver));
        //commandMap.put("execute_script", new ExecuteScript(receiver));
        commandMap.put("add_if_min", new AddIfMin(receiver));
        commandMap.put("remove_greater", new RemoveGreater(receiver));
        commandMap.put("remove_lower", new RemoveLower(receiver));
        commandMap.put("filter_by_car", new FilterByCar(receiver));
        commandMap.put("print_unique_car", new PrintUniqueCar(receiver));
        commandMap.put("print_field_descending_mood", new PrintFieldDescendingMood(receiver));
        commandMap.put("connect", new Connect());
    }

    public Response execute(String commandName, String[] args, Object obj) throws IOException {
        Command command = commandMap.get(commandName);
        Response response = command.execute(args, obj);
        new Save(receiver).execute(null,null);
        return response;
    }
    public Response execute(String commandName, String[] args) throws  IOException {
        Command command = commandMap.get(commandName);
        Response response = command.execute(args, null);
        new Save(receiver).execute(null,null);
        return response;
    }

    public static HashMap<String, Command> getCommandMap(){
        return commandMap;
    }

}
