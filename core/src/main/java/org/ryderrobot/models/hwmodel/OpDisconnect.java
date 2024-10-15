package org.ryderrobot.models.hwmodel;

import org.ryderrobot.models.hwmodel.payloads.Disconnect;

/**
 * Informs drone that it should disconnect. This command should be issued whenever the program is about to exit,
 * close connection or an unexpected error has occurred. It will stop the drone from keeping an open connection,
 * after the program finishes.
 */
public class OpDisconnect implements Command<Disconnect> {
    private final Action action;
    private final Disconnect payload;

    public OpDisconnect() {
        action = new Action(OpCodes.OP_DISCONNECT);
        payload = new Disconnect();
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Disconnect getPayload() {
        return payload;
    }
}
