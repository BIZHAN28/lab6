import managers.Invoker;
import commands.network.Request;
import commands.network.Response;
import managers.CollectionManager;
import commands.ScriptStack;

import java.io.IOException;
import java.util.Objects;

public class CommandProcessor {
    private CollectionManager collectionManager;
    private ScriptStack scriptStack;
    private Invoker invoker;

    public CommandProcessor(CollectionManager collectionManager, ScriptStack scriptStack) {
        this.collectionManager = collectionManager;
        this.scriptStack = scriptStack;
        this.invoker = new Invoker(collectionManager, scriptStack);
        collectionManager.loadFromXML();
    }

    public Response processRequest(Request request) throws IOException {
        if (Objects.equals(request.getCommandName(), "next")){
            return new Response("next");
        }
        return invoker.execute(request.getCommandName(), request.getArguments(), request.getElement());
    }
}
