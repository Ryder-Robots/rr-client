package org.ryderrobot.models.protocols.rrp;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRrpCurator {
    RrpCurator curator = new RrpCurator();

    @Test
    void shouldDeserializeWithPayload() {
        String j = "{\"command\":\"MSP_ERROR\", \"payload\":{\"message\":\"test this\"}}";
        Json json = new Json();
        RrpRequest request = json.fromJson(RrpRequest.class, j);
        RrpEvent<MspErrorPayload> event = curator.curate(request);

        assertTrue(event.hasPayload());
        assertEquals("test this", event.getPayload().getMessage());
    }

    @Test
    void shouldDeserializeWithoutPayload() {
        String j = "{\"command\":\"MSP_ERROR\"}";
        Json json = new Json();
        RrpRequest request = json.fromJson(RrpRequest.class, j);
        RrpEvent<MspErrorPayload> event = curator.curate(request);
        assertFalse(event.hasPayload());
    }

    @Test
    void shouldSerializeWithPayload() {
        MspErrorPayload payload = new MspErrorPayload("test something else");
        RrpEvent<MspErrorPayload> event = new RrpEvent<>(RrpCommands.MSP_ERROR, payload);
        String out = curator.toJson(event);
    }

    @Test
    void shouldSerializeWithoutPayload() {
        RrpEvent<MspErrorPayload> event = new RrpEvent<>(RrpCommands.MSP_ERROR);
        String out = curator.toJson(event);
    }
}
