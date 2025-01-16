package org.ryderrobot.models.protocols.rrp;

/**
 * send MSP 104 motor command to drone.
 */
public class Msp104MotorPayload {

    /**
     * contucts payload instance
     *
     * @param roll rotate along length.
     * @param pitch move forward or backwards (1 full pitch forward, -1 full pitch backwards)
     * @param yaw rotate along length
     * @param throttle increase or decrease throttle.
     */
    public Msp104MotorPayload(float roll, float pitch, float yaw, float throttle) {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
        this.throttle = throttle;
        aux1 = 0;
        aux2 = 0;
        aux3 = 0;
        aux4 = 0;
    }

    public float getRoll() {
        return roll;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getThrottle() {
        return throttle;
    }

    public float getAux1() {
        return aux1;
    }


    public float getAux2() {
        return aux2;
    }

    public float getAux3() {
        return aux3;
    }

    public float getAux4() {
        return aux4;
    }

    private float roll;
    private float pitch;
    private float yaw;
    private float throttle;
    private float aux1;
    private float aux2;
    private float aux3;
    private float aux4;
}
