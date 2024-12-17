package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Optional;

public abstract class RrpCurator<P> implements Json.Serializer<RrpEvent<P>> {
    @Override
    public void write(Json json, RrpEvent<P> pRrpEvent, Class aClass) {
        json.writeArrayStart();
        json.writeValue("command", pRrpEvent.getCommand());

        if (pRrpEvent.hasPayload()) {
            JsonValue payload = writePayload(pRrpEvent.getPayload());
            json.writeValue("payload", payload);
        }
        json.writeObjectEnd();
    }

    @Override
    public RrpEvent<P> read(Json json, JsonValue jsonData, Class aClass) {
        RrpEvent<P> event;
        RrpCommands command = RrpCommands.valueOf(
            jsonData.getString("command", RrpCommands.MSP_NONE.name()));
        Optional<P> payload = Optional.empty();
        if (jsonData.has("payload")) {
            payload = Optional.of(readPayload(jsonData));
        }

        event = payload.map(p -> new RrpEvent<>(command, p)).orElseGet(() ->
            new RrpEvent<>(command));
        return event;
    }

    protected abstract JsonValue writePayload(P payload);

    protected abstract P readPayload(JsonValue jsonData);
}
