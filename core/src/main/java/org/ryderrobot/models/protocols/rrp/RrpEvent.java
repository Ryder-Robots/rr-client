package org.ryderrobot.models.protocols.rrp;

import java.util.Optional;

public class RrpEvent<P> {
    public RrpEvent(RrpCommands command, P payload) {
        this.command = command;
        this.payload = Optional.of(payload);
    }

    public RrpEvent(RrpCommands command) {
        this.command = command;
    }

    public boolean hasPayload() {
        return payload.isPresent();
    }

    public P getPayload() {
        P payload = null;
        if (this.payload.isPresent()) {
            payload = this.payload.get();
        }
        return payload;
    }

    public RrpCommands getCommand() {
        return command;
    }

    private final RrpCommands command;
    private Optional<P> payload = Optional.empty();
}
