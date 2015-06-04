package en.ibbo.hades.server;

import java.net.Socket;

/**
 * Created by thoma_000 on 04/06/2015.
 */
public class Connection {
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public String toString() {
        return "Connection[remote_address=" + socket.getRemoteSocketAddress() + "];";
    }
}
