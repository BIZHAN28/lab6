package commands;

import commands.network.Response;
import managers.Receiver;

import java.io.IOException;

public class Save implements Command{
    private final Receiver receiver;
    public Save(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public Response execute(String[] args, Object obj) throws IOException {
        return receiver.save(args);
    }
}
