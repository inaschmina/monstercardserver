package HttpRequest;

import java.io.IOException;
import java.net.Socket;

public interface RequestHandler {
    public void readRequest(Socket socket) throws IOException;
}
