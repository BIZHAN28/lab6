package commands;

import commands.network.Response;
import managers.Receiver;
import models.HumanBeing;
import validation.CommandInfo;

@CommandInfo(name = "Add_If_Min", requiredObjectType = HumanBeing.class)
public class AddIfMin implements Command{
    private final Receiver commandReceiver;
        public AddIfMin(Receiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }
    @Override
    public Response execute(String[] args, Object obj) {
        return commandReceiver.addIfMin(args, obj);
    }
}
