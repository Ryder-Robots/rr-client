package org.ryderrobot.models.hwmodel;

public class Action {
    private final OpCodes op;
    private final float value;

    public Action(OpCodes op, float value) {
        this.op = op;
        this.value = value;
    }

    public OpCodes getOp() {
        return op;
    }

    public float getValue() {
        return value;
    }
}
