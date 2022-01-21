package server;

import java.io.IOException;
import java.net.Socket;

public interface serverInterface {
    public void readRequest(Socket socket) throws IOException;
}
