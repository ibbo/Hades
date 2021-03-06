package en.ibbo.hades.server.test;

import en.ibbo.hades.server.SendableEvent;

import java.util.concurrent.BlockingQueue;

/**
 * Sends dummy events periodically.
 */
public class DummyEventCreator implements Runnable {

    private final BlockingQueue<SendableEvent> eventQueue;

    public DummyEventCreator(BlockingQueue<SendableEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                eventQueue.put(new TestEvent());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
