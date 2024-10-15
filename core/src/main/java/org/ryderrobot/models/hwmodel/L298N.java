package org.ryderrobot.models.hwmodel;

import org.ryderrobot.models.hwmodel.l298.In;

/**
 * motor driver for L298N. (H bridge).
 * <p>
 * for ena, and enb 1 indicates maximum voltage, 0 indicates no voltage, and any value between that is the voltage
 * level.
 */
public class L298N {
    // value between 0 and 1
    private final float ea;

    // value between 0 and 1
    private final float eb;

    private final In in;  // flags for IN1, IN2, IN3 and IN4

    public L298N(float ea, float eb, In in) {
        this.ea = ea;
        this.eb = eb;
        this.in = in;
    }

    public float getEa() {
        return ea;
    }

    public float getEb() {
        return eb;
    }

    public In getIna() {
        return in;
    }
}
