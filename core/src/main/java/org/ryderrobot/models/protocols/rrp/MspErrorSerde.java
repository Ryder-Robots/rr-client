package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspErrorSerde implements RrpSerde<MspErrorPayload>{
    @Override
    public JsonValue serialize(MspErrorPayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("message", new JsonValue(payload.getMessage()));
        return jsonObject;
    }
}
