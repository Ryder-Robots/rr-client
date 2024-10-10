package org.ryderrobot.models.hwmodel;

public enum OpCodes {
    RR_CMD_U1((short) 0x05), /* voltage value land motor driver A */
    RR_CMD_U2((short) 0x06), /* voltage value land motor Driver B */
    RR_CMD_U3((short) 0x07), /* polarity setting for motor driver A */
    RR_CMD_U4((short) 0x08),;/* polarity setting for motor driver B */

    private short opCode;

    OpCodes(short opCode) {
        this.opCode = opCode;
    }

    public short getOpCode() {
        return opCode;
    }
}
