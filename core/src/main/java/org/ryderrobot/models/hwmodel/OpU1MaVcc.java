package org.ryderrobot.models.hwmodel;

/**
 * Command for a single H-bridge motor.
 */
public class OpU1MaVcc implements Command<L298N> {
    private Action action;
    private L298N payload;

    public OpU1MaVcc(Action action, L298N payload) {
        this.action = action;
        this.payload = payload;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public L298N getPayload() {
        return payload;
    }
}
