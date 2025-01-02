package org.ryderrobot.models.protocols.rrp;

public class MspHbridgePayload {
    private final float motor1, motor2, motor3, motor4;
    private final short in;

    public MspHbridgePayload(short in, float motor1, float motor2,float motor3, float motor4) {
        this.in = in;
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.motor3 = motor3;
        this.motor4 = motor4;
    }

    public short getIn() {
        return in;
    }

    public float getMotor1() {
        return motor1;
    }

    public float getMotor2() {
        return motor2;
    }

    public float getMotor3() {
        return motor3;
    }

    public float getMotor4() {
        return motor4;
    }
}
