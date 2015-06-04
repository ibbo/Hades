package en.ibbo.hades.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectionServer implements Runnable {
    private final EventSender eventSender;
    private final int port;

    public ConnectionServer(int port, EventSender eventSender) throws IOException {
        this.port = port;
        this.eventSender = eventSender;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // TODO: Change this with proper logging
            System.out.println("Accepting connections on port: " + port);
            while (true) {
                Socket newSocket;
                try {
                    newSocket = serverSocket.accept();
                    System.out.println("Connected to new client: " + newSocket.getRemoteSocketAddress());
                    eventSender.addConnection(new Connection(newSocket));
                } catch (SocketTimeoutException s) {
                    // Do nothing, just wait for more connections
                } catch (IOException ex) {
                    System.out.println("Error while running event server.");
                    ex.printStackTrace(System.out);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
