package hades.server;

public final class SwitchEvent implements SendableEvent {

    private final String id;
    private final boolean switchState;

    public SwitchEvent(String id, boolean switchState) {
        this.id = id;
        this.switchState = switchState;
    }

    @Override
    public String getEventData() {
        return id + ": " + switchState;
    }
}
