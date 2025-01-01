package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestSerdeFactory {
    SerdeFactory serdeFactory = new SerdeFactory();

    @Test
    public void shouldDeserializeHbridge() {
        MspHbridgePayload mspHbridgePayload = new MspHbridgePayload((short)1, 100f, 200f, 300f, 400f);
        RrpEvent<MspHbridgePayload> event = new RrpEvent<>(RrpCommands.MSP_SET_MOTOR_HBRIDGE, mspHbridgePayload);

        JsonValue payload = serdeFactory.getSerde(event.getCommand()).serialize(event.getPayload());

        assertEquals((short)1, payload.get("in").asShort());
        assertEquals(100f, payload.get("motor1").asFloat());
        assertEquals(200f, payload.get("motor2").asFloat());
        assertEquals(300f, payload.get("motor3").asFloat());
        assertEquals(400f, payload.get("motor4").asFloat());
    }
}
