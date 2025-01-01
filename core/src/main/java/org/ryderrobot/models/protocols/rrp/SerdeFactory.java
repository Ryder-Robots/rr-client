package org.ryderrobot.models.protocols.rrp;

import java.util.Map;

public class SerdeFactory {

    private final Map<RrpCommands, RrpSerde> serdes = Map.of(
        RrpCommands.MSP_SET_MOTOR_HBRIDGE, new MspHbridgeSerde(),
        RrpCommands.MSP_ERROR, new MspErrorSerde()
    );

    public RrpSerde getSerde(RrpCommands command) {
        return serdes.get(command);
    }
}
