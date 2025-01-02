package org.ryderrobot.models.protocols.rrp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRrpCurator {
    RrpCurator curator = new RrpCurator();

    @Test
    void shouldDeserializeWithPayload() {
        String  j = "{\"command\":\"MSP_ERROR\", \"payload\":{\"message\":\"test this\"}}";
        RrpEvent<MspErrorPayload> event = curator.curate(j);

        assertTrue(event.hasPayload());
        assertEquals("test this", event.getPayload().getMessage());
    }

    @Test
    void shouldDeserializeWithoutPayload() {
        String j = "{\"command\":\"MSP_ERROR\"}";
        RrpEvent<MspErrorPayload> event = curator.curate(j);
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
