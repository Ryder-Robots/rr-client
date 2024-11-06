package org.ryderrobot.models.hwmodel.payloads;

import org.ryderrobot.models.hwmodel.l298.In;

/**
 * Land Drones that use four independent motors. Each motor is driven by a H Bridge.
 */
public class LandDrone4Wheel {
    // value between 0 and 1
    private final float ea_m1;

    // value between 0 and 1
    private final float eb_m1;

    private final short in_m1;

    // value between 0 and 1
    private final float ea_m2;

    // value between 0 and 1
    private final float eb_m2;

    private final short in_m2;

    public LandDrone4Wheel(float ea_m1, float eb_m1, In in_m1, float ea_m2, float eb_m2, In in_m2) {
        this.ea_m1 = ea_m1;
        this.eb_m1 = eb_m1;
        this.in_m1 = in_m1.getValue();
        this.ea_m2 = ea_m2;
        this.eb_m2 = eb_m2;
        this.in_m2 = in_m2.getValue();
    }
}
