import commands.network.CommandDescription;
import commands.network.Request;
import exceptions.RecursiveException;
import managers.AskManager;
import managers.FileInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScriptExecutor {
    public static List<Request> execute(String filename, HashMap<String, CommandDescription> commands, ScriptStack scriptStack){

        List<Request> requests = new ArrayList<>();
        try {
            File file = new File(filename);
            if (scriptStack.contains(filename)) {
                throw new RecursiveException();
            }
            scriptStack.push(filename);
            FileInput fileInput = new FileInput(file);
            AskManager askManager = new AskManager(fileInput);

            while (fileInput.handleInput().hasNextLine()) {
                String s = fileInput.handleInput().nextLine();
                String[] spl = s.split(" ", 2);
                Object obj = null;
                if (commands.containsKey(spl[0])) {
                    if (commands.get(spl[0]).getRequiredObjectType() != Void.class) {
                        obj = askManager.ask(commands.get(spl[0]).getRequiredObjectType().getConstructor().newInstance());
                    }
                    requests.add(new Request(spl[0],Arrays.copyOfRange(spl, 1, spl.length), obj));
                } else if (spl[0].equalsIgnoreCase("execute_script")){
                    ScriptExecutor.execute(spl[1],commands,scriptStack);
                }

            }
            scriptStack.pop();
            return requests;
        } catch (FileNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
