package org.ryderrobot.models.protocols.rrp;

import java.util.HashMap;
import java.util.Map;

public class RrpRequest {
    public void setCommand(RrpCommands command) {
        this.command = command;
    }

    public RrpCommands getCommand() {
        return command;
    }

    public void setPayload(HashMap<String, String> payload) {
        this.payload = payload;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    private RrpCommands command;
    private HashMap<String, String> payload;
}
