package org.ryderrobot.models.protocols.rrp;

public class MspStatus {
    public MspStatus(final int cycletime,
                     final int i2c_errors_count,
                     final int sensor,
                     final int flag,
                     final int current_set) {
        this.cycletime = cycletime;
        this.i2c_errors_count = i2c_errors_count;
        this.sensor = sensor;
        this.flag = flag;
        this.current_set = current_set;
    }

    public int getCycletime() {
        return cycletime;
    }

    public int getI2c_errors_count() {
        return i2c_errors_count;
    }

    public int getSensor() {
        return sensor;
    }

    public int getFlag() {
        return flag;
    }

    public int getCurrent_set() {
        return current_set;
    }

    private final int cycletime;
    private final int i2c_errors_count;
    private final int sensor;
    private final int flag;
    private final int current_set;
}
