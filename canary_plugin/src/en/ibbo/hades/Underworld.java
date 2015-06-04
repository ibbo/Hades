package en.ibbo.hades;

import en.ibbo.hades.server.EventSender;
import en.ibbo.hades.server.EventServer;
import net.canarymod.Canary;
import net.canarymod.plugin.Plugin;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Underworld extends Plugin {

    private EventServer eventServer;
    private EventSender eventSender;
    private ExecutorService executorService;

    @Override
    public boolean enable() {
        int port = 18000;
        try {
            eventSender = new EventSender();
            eventServer = new EventServer(port, eventSender);
        } catch (IOException e) {
            getLogman().error("Failed to create event server.", e);
        }

        Canary.hooks().registerListener(new SwitchListener(eventSender.getEventQueue()), this);
        getLogman().info("Enabling " + getName() + " Version " + getVersion());
        getLogman().info("Authored by " + getAuthor());

        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(eventSender);
        executorService.submit(eventServer);
        return true;
    }

    @Override
    public void disable() {
        getLogman().info("Disabling " + getName());
        executorService.shutdownNow();
    }
}
