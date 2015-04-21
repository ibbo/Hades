package hades.server.test;

import hades.server.SendableEvent;

public class TestEvent implements SendableEvent {
    @Override
    public String getEventData() {
        return "1:true";
    }
}
