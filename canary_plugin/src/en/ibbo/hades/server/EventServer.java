package en.ibbo.hades.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventServer implements Runnable {
    private final EventSender eventSender;
    private final int port;

    public EventServer(int port, EventSender eventSender) throws IOException {
        this.port = port;
        this.eventSender = eventSender;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket newSocket;
                try {
                    // TODO: Change this with proper logging
                    System.out.println("Accepting connections on port: " + port);
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
