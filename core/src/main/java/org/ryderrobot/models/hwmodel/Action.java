package org.ryderrobot.models.hwmodel;

public class Action {
    private OpCodes op;
    private float value;

    public Action() {

    }

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
