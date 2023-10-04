import commands.ScriptStack;
import commands.network.Request;
import commands.network.Response;
import managers.CollectionManager;
import managers.Receiver;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {
    public static void main(String[] args) {
        try {
            int port = 6789;

            ConnectionHandler connectionHandler = new ConnectionHandler(port);
            RequestReader requestReader = new RequestReader(100000);
            CommandProcessor commandProcessor = new CommandProcessor(new CollectionManager(), new ScriptStack());
            ResponseSender responseSender = new ResponseSender();


            Selector selector = connectionHandler.getSelector();

            while (true) {
                connectionHandler.acceptConnections();

                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        try {
                            Request request = requestReader.readRequest(client);
                            if (request != null) {
                                Response response = commandProcessor.processRequest(request);
                                responseSender.sendResponse(client, response);
                            }
                        } catch (SocketException e) {
                            connectionHandler.removeConnection(client);

                        }
                    }

                    keyIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
