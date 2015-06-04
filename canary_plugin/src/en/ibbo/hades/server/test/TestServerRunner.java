package en.ibbo.hades.server.test;

import en.ibbo.hades.server.ConnectionServer;
import en.ibbo.hades.server.EventSender;
import en.ibbo.hades.server.SendableEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServerRunner {
    public static void main(String[] args) throws Exception {
        EventSender eventSender = new EventSender();
        ConnectionServer connectionServer = new ConnectionServer(18000, eventSender);
        BlockingQueue<SendableEvent> eventQueue = eventSender.getEventQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(eventSender);
        executorService.submit(connectionServer);
        DummyEventCreator eventCreator = new DummyEventCreator(eventQueue);
        executorService.submit(eventCreator);
        // Shut down once the current threads have finished executing.
        executorService.shutdown();
    }
}
