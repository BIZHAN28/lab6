package commands;

import commands.network.Response;
import managers.Receiver;
import models.HumanBeing;
import validation.CommandInfo;

@CommandInfo(name = "Remove_Greater", requiredObjectType = HumanBeing.class)
public class RemoveGreater implements Command{
    private final Receiver receiver;
    public RemoveGreater(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) {
        return receiver.removeGreater(args, obj);
    }
}
