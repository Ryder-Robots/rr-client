package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspModeSerde implements RrpSerde<MspModePayload> {
    @Override
    public JsonValue serialize(MspModePayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("mode", new JsonValue(payload.getMode().name()));
        return jsonObject;
    }

    @Override
    public MspModePayload deserialize(JsonValue payload) {
        return new MspModePayload(MspMode.valueOf(payload.get("mode").asString()));
    }
}
