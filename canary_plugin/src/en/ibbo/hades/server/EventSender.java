package en.ibbo.hades.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by thoma_000 on 04/06/2015.
 */
public class EventSender implements Runnable {

    private final BlockingQueue<SendableEvent> eventQueue = new ArrayBlockingQueue<>(10);

    private final List<Connection> allConnections = Collections.synchronizedList(new ArrayList<>());

    public void addConnection(Connection connection) {
        allConnections.add(connection);
    }

    public BlockingQueue<SendableEvent> getEventQueue() {
        return eventQueue;
    }

    @Override
    public void run() {
        while (true) {
            SendableEvent event;
            try {
                event = eventQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
                Thread.currentThread().interrupt();
                break;
            }
            if (!allConnections.isEmpty()) {
                // Make a copy of the collections list and iterate over that.
                List<Connection> currentConnections = new ArrayList<>(allConnections);
                for (Connection c : currentConnections) {
                    sendEventData(c, event);
                }
            }
        }
    }

    private void sendEventData(Connection thisConnection, SendableEvent event) {
        try {
            Writer outputStream = new OutputStreamWriter(thisConnection.getSocket().getOutputStream(), "UTF-8");
            System.out.println("Sending: " + event.getEventData() + " to: " + thisConnection);
            outputStream.write(event.getEventData());
            outputStream.write("\n");
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Connection dropped." + e);
            allConnections.remove(thisConnection);
        }
    }
}
