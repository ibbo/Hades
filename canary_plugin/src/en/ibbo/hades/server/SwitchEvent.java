package en.ibbo.hades.server;

public final class SwitchEvent implements SendableEvent {

    private final String id;
    private final boolean switchState;
    private final String additionalInfo;

    public SwitchEvent(String id, boolean switchState, String additionalInfo) {
        this.id = id;
        this.switchState = switchState;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String getEventData() {
        if ("".equals(additionalInfo)) {
            return id + ": " + switchState;
        } else {
            return id + ": " + switchState + "," + additionalInfo;
        }
    }
}
