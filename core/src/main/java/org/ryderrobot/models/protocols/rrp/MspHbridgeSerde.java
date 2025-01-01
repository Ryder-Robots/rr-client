package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspHbridgeSerde implements RrpSerde<MspHbridgePayload>{

    @Override
    public JsonValue serialize(MspHbridgePayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("in", new JsonValue(payload.getIn()));
        jsonObject.addChild("motor1", new JsonValue(payload.getMotor1()));
        jsonObject.addChild("motor2", new JsonValue(payload.getMotor2()));
        jsonObject.addChild("motor3", new JsonValue(payload.getMotor3()));
        jsonObject.addChild("motor4", new JsonValue(payload.getMotor4()));

        return jsonObject;
    }
}
