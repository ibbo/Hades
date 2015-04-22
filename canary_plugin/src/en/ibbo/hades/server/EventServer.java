package en.ibbo.hades.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventServer implements Runnable {
    private final ServerSocket serverSocket;

    private final int port;

    private final BlockingQueue<SendableEvent> eventQueue = new ArrayBlockingQueue<>(10);

    public EventServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
        serverSocket.setSoTimeout(10000);
    }

    public BlockingQueue<SendableEvent> getEventQueue() {
        return eventQueue;
    }

    public void run() {
        while(true) {
            Socket server = null;
            try {
                // TODO: Change this with proper logging
                System.out.println("Accepting connections on port: " + port);
                server = serverSocket.accept();
                System.out.println("Connected to client, sending events.");
                sendEventsToClient(server);
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out, no connection made, retrying.");
            } catch (IOException ex) {
                System.out.println("Error while running event server.");
                ex.printStackTrace(System.out);
                break;
            } finally {
                if (server != null) {
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace(System.out);
                    }
                }
            }
        }
    }

    private void sendEventsToClient(Socket server) throws IOException {
        try (Writer outputStream = new OutputStreamWriter(server.getOutputStream(), "UTF-8")) {
            while (true) {
                SendableEvent event;
                try {
                    event = eventQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                    Thread.currentThread().interrupt();
                    break;
                }
                System.out.println("Sending: " + event.getEventData());
                outputStream.write(event.getEventData());
                outputStream.flush();
            }
        }
    }
}
