package commands;

import commands.network.Response;
import managers.Receiver;
import validation.CommandInfo;

@CommandInfo(name="Clear")
public class Clear implements Command{
    private final Receiver receiver;
    public Clear(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) {
        return receiver.clear(args);
    }
}
