import commands.network.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConnectionHandler {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private List<SocketChannel> connectedClients;

    public ConnectionHandler(int port) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(host, port));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        connectedClients = new ArrayList<>();
    }

    public void acceptConnections() throws IOException {
            int readyChannels = selector.select();
            if (readyChannels > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if (socketChannel != null && connectedClients.size() == 0) {
                            socketChannel.configureBlocking(false);
                            connectedClients.add(socketChannel);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (socketChannel != null){
                            socketChannel.configureBlocking(false);
                            ResponseSender responseSender = new ResponseSender();
                            responseSender.sendResponse(socketChannel, new Response("The server is currently busy. Come back later."));

                        }
                    }
                    keyIterator.remove();
                }
            }
    }
    public void removeConnection(SocketChannel socketChannel) throws IOException {
        if (connectedClients.contains(socketChannel)) {
            socketChannel.close();
            connectedClients.remove(socketChannel);
        }
    }


    public List<SocketChannel> getConnectedClients() {
        return connectedClients;
    }

    public void close() throws IOException {
        serverSocketChannel.close();
    }

    public Selector getSelector() {
        return selector;
    }
}
