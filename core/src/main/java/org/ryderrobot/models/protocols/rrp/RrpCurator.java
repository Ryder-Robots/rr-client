package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.exceptions.MissingRequiredAttribute;
import org.ryderrobot.exceptions.MissingSerdeException;
import java.util.Optional;

public class RrpCurator {
    private final SerdeFactory serdeFactory = new SerdeFactory();

    public RrpEvent curate(String j) {
        JsonValue request = (new JsonReader()).parse(j);

        if (!request.has("command")) {
            throw new MissingRequiredAttribute("command is a required attribute");
        }

        RrpEvent event;
        if (request.has("payload")) {
            RrpSerde serde = serdeFactory.getSerde(RrpCommands.valueOf(request.get("command").asString()));
            if (Optional.ofNullable(serde).isEmpty()) {
                throw new MissingSerdeException(request.get("command").asString() + " command is unsupported");
            }
            event = new RrpEvent<>(
                RrpCommands.valueOf(request.get("command").asString()),
                serde.deserialize(request.get("payload")));
        } else {
            event = new RrpEvent(RrpCommands.valueOf(request.get("command").asString()));
        }

        return event;
    }

    public String toJson(RrpEvent event) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("command", new JsonValue(event.getCommand().name()));

        if (event.hasPayload()) {
            RrpSerde serde = serdeFactory.getSerde(event.getCommand());
            if (Optional.ofNullable(serde).isEmpty()) {
                throw new MissingSerdeException("no available serde");
            }
            JsonValue payload = serde.serialize(event.getPayload());
            jsonObject.addChild("payload", payload);
        }
        return jsonObject.toJson(JsonWriter.OutputType.json);
    }
}
