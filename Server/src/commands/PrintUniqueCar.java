package commands;

import commands.network.Response;

import managers.Receiver;
import validation.CommandInfo;

@CommandInfo(name = "Print_Unique_Car")
public class PrintUniqueCar implements Command{
    private final Receiver receiver;
    public PrintUniqueCar(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) {
        return receiver.printUniqueCar(args);
    }
}
