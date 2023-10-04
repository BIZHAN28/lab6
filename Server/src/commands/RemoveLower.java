package commands;

import commands.network.Response;
import managers.Receiver;
import models.HumanBeing;
import validation.CommandInfo;

@CommandInfo(name = "Remove_Lower", requiredObjectType = HumanBeing.class)
public class RemoveLower implements Command{
    private final Receiver receiver;
    public RemoveLower(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) {
        return receiver.removeLower(args, obj);
    }
}
