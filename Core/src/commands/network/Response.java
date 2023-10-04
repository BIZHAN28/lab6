package commands.network;

import java.io.Serializable;

public class Response implements Serializable {
    private final String message;
    private final Object object;

    public Response(String message, Object object) {
        this.message = message;
        this.object = object;
    }
    public Response(String message) {
        this.message = message;
        this.object = null;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
