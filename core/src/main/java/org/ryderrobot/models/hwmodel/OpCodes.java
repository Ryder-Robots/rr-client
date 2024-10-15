package org.ryderrobot.models.hwmodel;

public enum OpCodes {
    OP_U1_MA_VCC((short)0x01),

    // this op code should be sent when exit or disconnect is needed on the drone.
    OP_DISCONNECT((short) 0x02);

    private final short opCode;

    OpCodes(short opCode) {
        this.opCode = opCode;
    }

    public short getOpCode() {
        return opCode;
    }
}
