import commands.network.Request;
import exceptions.ByteLossException;
import managers.SerializeManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RequestReader {
    private ByteBuffer buffer;
    private SerializeManager serializeManager = new SerializeManager();

    public RequestReader(int bufferSize) {
        buffer = ByteBuffer.allocate(bufferSize);
    }

    public Request readRequest(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        return (Request) serializeManager.deserialize(buffer.array());
    }
}
