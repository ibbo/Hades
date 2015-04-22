package en.ibbo.hades.server.test;

import en.ibbo.hades.server.SendableEvent;

public class TestEvent implements SendableEvent {
    @Override
    public String getEventData() {
        return "1:true";
    }
}
