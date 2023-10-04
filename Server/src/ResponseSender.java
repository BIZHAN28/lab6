import commands.network.Response;
import managers.SerializeManager;
import validation.MaxValue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResponseSender {
    private SerializeManager serializeManager;
    private int responseId = 0;
    private String responseMessage;

    public ResponseSender() {
        serializeManager = new SerializeManager();
    }

    public void sendResponse(SocketChannel socketChannel, Response response) throws IOException {

            if (responseId == 0){
                responseMessage = response.getMessage();
            }
            if (responseId == responseMessage.split("\n").length){
                socketChannel.write(ByteBuffer.wrap(serializeManager.serialize(new Response("#end"))));
                responseId = 0;
            }else {
                String s = responseMessage.split("\n")[responseId];
                byte[] responseData = serializeManager.serialize(new Response(s, response.getObject()));
                ByteBuffer buffer = ByteBuffer.wrap(responseData);
                socketChannel.write(buffer);
                responseId++;
            }

    }

}
