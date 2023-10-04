package commands;

import commands.network.Response;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface Command {
    Response execute(String[] args, Object obj) throws IOException;

}
