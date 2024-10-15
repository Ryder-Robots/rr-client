package org.ryderrobot.models.hwmodel;

/**
 * base template for creating actions.
 */

public class Action {
    private final OpCodes op;

    public Action(OpCodes op) {
        this.op = op;
    }

    public OpCodes getOp() {
        return op;
    }
}
