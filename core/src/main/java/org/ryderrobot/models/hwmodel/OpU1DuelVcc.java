package org.ryderrobot.models.hwmodel;

import org.ryderrobot.models.hwmodel.payloads.LandDrone4Wheel;

/**
 * Command for a duel H-bridge motor.
 */
public class OpU1DuelVcc  implements Command<LandDrone4Wheel> {
    private Action action;
    private LandDrone4Wheel payload;

    public OpU1DuelVcc(LandDrone4Wheel payload) {
        this.action = new Action(OpCodes.OP_U1_MA_VCC);
        this.payload = payload;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public LandDrone4Wheel getPayload() {
        return payload;
    }
}
