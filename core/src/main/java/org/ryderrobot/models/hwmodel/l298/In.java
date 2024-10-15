package org.ryderrobot.models.hwmodel.l298;

/**
 * flags used to drive motors.
 */

public enum In {
    EN_FORWARD((short) 0b0101),
    EN_BACKWARD((short) 0b1010),
    EN_STOP((short) 0x0000);

    private final short value;

    In(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
