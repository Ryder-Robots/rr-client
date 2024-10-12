package org.ryderrobot.models.hwmodel;

public enum OpCodes {
    OP_U1_MA_VCC((short)0x01),
    OP_U1_CMD((short) 0x02);

    private short opCode;

    OpCodes(short opCode) {
        this.opCode = opCode;
    }

    public short getOpCode() {
        return opCode;
    }
}
