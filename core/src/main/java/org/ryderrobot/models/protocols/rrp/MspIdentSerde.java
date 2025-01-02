package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspIdentSerde implements RrpSerde<MspIdentPayload> {

    @Override
    public JsonValue serialize(MspIdentPayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("version", new JsonValue(payload.getVersion()));
        jsonObject.addChild("multitype", new JsonValue(payload.getMultitype().name()));
        jsonObject.addChild("msp_version", new JsonValue(payload.getMspversion().name()));
        jsonObject.addChild("capability", new JsonValue(payload.getCapability()));
        return jsonObject;
    }

    @Override
    public MspIdentPayload deserialize(JsonValue payload) {
        return new MspIdentPayload(
            payload.get("version").asInt(),
            MultiType.valueOf(payload.get("multitype").asString()),
            MspVersion.valueOf(payload.get("msp_version").asString()),
            payload.get("capability").asInt()
        );
    }
}
