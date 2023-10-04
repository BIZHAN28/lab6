package commands;

import commands.network.Response;
import managers.Receiver;
import models.Car;
import validation.CommandInfo;

@CommandInfo(name = "Filter_By_Car", requiredObjectType = Car.class)
public class FilterByCar implements Command{
    private final Receiver receiver;
    public FilterByCar(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public Response execute(String [] args, Object obj) {
        return receiver.filterByCar(args, obj);
    }
}
