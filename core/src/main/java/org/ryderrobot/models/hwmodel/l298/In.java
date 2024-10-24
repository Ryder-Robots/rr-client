package org.ryderrobot.models.hwmodel.l298;

/**
 * flags used to drive motors.
 */

public enum In {
    EN_BACKWARD((short) 0b0101),
    EN_FORWARD((short) 0b1010),
    EN_STOP((short) 0b0000),
    EN_LEFT((short) 0b1001),
    EN_RIGHT((short) 0b0110);

    private final short value;

    In(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
