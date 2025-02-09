package org.ryderrobot.models.protocols.rrp;

import java.util.Map;

/**
 * Returns SERDE for supported commands.
 */
public class SerdeFactory {

    private final Map<RrpCommands, RrpSerde> serdes = Map.of(
        RrpCommands.MSP_SET_MOTOR_HBRIDGE, new MspHbridgeSerde(),
        RrpCommands.MSP_MOTOR, new MspMotorSerde(),
        RrpCommands.MSP_ERROR, new MspErrorSerde(),
        RrpCommands.MSP_IDENT, new MspIdentSerde(),
        RrpCommands.MSP_STATUS, new MspStatusSerde(),
        RrpCommands.MSP_MODE, new MspModeSerde()
    );

    public RrpSerde getSerde(RrpCommands command) {
        return serdes.get(command);
    }
}
