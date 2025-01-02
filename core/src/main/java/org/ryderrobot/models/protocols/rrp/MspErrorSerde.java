package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;
import org.ryderrobot.exceptions.MissingRequiredAttribute;

public class MspErrorSerde implements RrpSerde<MspErrorPayload>{
    @Override
    public MspErrorPayload deserialize(JsonValue payload) {
        if (!payload.has("message")) {
            throw new MissingRequiredAttribute("message is a required attribute");
        }
        return new MspErrorPayload(payload.get("message").asString());
    }

    @Override
    public JsonValue serialize(MspErrorPayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("message", new JsonValue(payload.getMessage()));
        return jsonObject;
    }
}
