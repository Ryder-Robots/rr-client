package org.ryderrobot.models.hwmodel;

import org.ryderrobot.models.hwmodel.payloads.L298N;

/**
 * Command for a single H-bridge motor.
 */
public class OpU1MaVcc implements Command<L298N> {
    private Action action;
    private L298N payload;

    public OpU1MaVcc(L298N payload) {
        this.action = new Action(OpCodes.OP_U1_MA_VCC);
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
