package org.ryderrobot.models.protocols.rrp;

import org.ryderrobot.exceptions.MissingRequiredAttribute;

import java.util.Optional;

/**
 * RRP Events with payloads.
 */
public class RrpEvent<P> {
    public RrpEvent(final RrpCommands command, final P payload) {
        this.command = command;
        this.payload = Optional.of(payload);
    }

    public RrpEvent(final RrpCommands command) {
        this.command = command;
        this.payload = Optional.empty();
    }


    public final RrpCommands getCommand() {
        return command;
    }

    public final P getPayload() {
        if (payload.isEmpty()) {
            throw new MissingRequiredAttribute("payload is not present");
        }
        return payload.get();
    }

    public boolean hasPayload() {
        return payload.isPresent();
    }

    private final RrpCommands command;
    private final Optional<P> payload;
}
