package managers;

import commands.network.Request;
import commands.network.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class SerializeManager {
    public byte[] serialize(Serializable command) {
        try (ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(command);
            outputStream.close();
            objectOutputStream.close();
            return outputStream.toByteArray();
        } catch (IOException ignore) {
            // as ByteArrayOutputStream is used, it should not throw any io exceptions
            throw new RuntimeException(ignore);
        }
    }
    public Object deserialize(byte[] data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return object;
        } catch (Exception ignore) {}
        return null;
    }


}
