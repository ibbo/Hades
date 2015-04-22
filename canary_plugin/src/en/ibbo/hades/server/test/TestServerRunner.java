package en.ibbo.hades.server.test;

import en.ibbo.hades.server.EventServer;
import en.ibbo.hades.server.SendableEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServerRunner {
    public static void main(String[] args) throws Exception {
        EventServer eventServer = new EventServer(18000);
        BlockingQueue<SendableEvent> eventQueue = eventServer.getEventQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(eventServer);
        DummyEventCreator eventCreator = new DummyEventCreator(eventQueue);
        executorService.submit(eventCreator);
        // Shut down once the current threads have finished executing.
        executorService.shutdown();
    }
}
