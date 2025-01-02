package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspStatusSerde implements RrpSerde<MspStatus> {
    @Override
    public JsonValue serialize(MspStatus payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("cycletime", new JsonValue(payload.getCycletime()));
        jsonObject.addChild("i2c_errors_count", new JsonValue(payload.getI2c_errors_count()));
        jsonObject.addChild("sensor", new JsonValue(payload.getSensor()));
        jsonObject.addChild("flag", new JsonValue(payload.getFlag()));
        jsonObject.addChild("current_set", new JsonValue(payload.getCurrent_set()));

        return jsonObject;
    }

    @Override
    public MspStatus deserialize(JsonValue payload) {
        return new MspStatus(
            payload.get("cycletime").asInt(),
            payload.get("i2c_errors_count").asInt(),
            payload.get("sensor").asInt(),
            payload.get("flag").asInt(),
            payload.get("current_set").asInt()
        );
    }
}
