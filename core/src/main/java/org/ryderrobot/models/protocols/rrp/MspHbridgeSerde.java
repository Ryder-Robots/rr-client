package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;
import org.ryderrobot.exceptions.MissingRequiredAttribute;

import java.util.Arrays;

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

    @Override
    public MspHbridgePayload deserialize(JsonValue payload) {
        for (String s : Arrays.asList("in", "motor1", "motor2", "motor3", "motor4")) {
            if (!payload.has(s)) {
                throw new MissingRequiredAttribute(s + " is a required attribute for MspHbridgePayload");
            }
        }

        return new MspHbridgePayload(
            payload.get("in").asShort(),
            payload.get("motor1").asFloat(),
            payload.get("motor2").asFloat(),
            payload.get("motor3").asFloat(),
            payload.get("motor4").asFloat()
        );
    }
}
