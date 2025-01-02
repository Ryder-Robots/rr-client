package org.ryderrobot.models.protocols.rrp;

public class MspModePayload {
    public MspModePayload(MspMode mode) {
        this.mode = mode;
    }

    public MspMode getMode() {
        return mode;
    }

    private MspMode mode;
}
