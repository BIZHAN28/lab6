package commands;

import commands.network.Response;
import managers.Receiver;
import validation.CommandInfo;

import java.io.IOException;

@CommandInfo(name = "execute_script", argsCount = 1)
public class ExecuteScript implements Command{
    private final Receiver receiver;
    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) throws IOException {
        return receiver.executeScript(args);
    }

}
