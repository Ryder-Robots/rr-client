package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public class MspMotorSerde  implements RrpSerde<Msp104MotorPayload> {
    @Override
    public JsonValue serialize(Msp104MotorPayload payload) {
        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("roll", new JsonValue(payload.getRoll()));
        jsonObject.addChild("pitch", new JsonValue(payload.getPitch()));
        jsonObject.addChild("yaw", new JsonValue(payload.getYaw()));
        jsonObject.addChild("throttle", new JsonValue(payload.getThrottle()));
        jsonObject.addChild("aux1", new JsonValue(payload.getAux1()));
        jsonObject.addChild("aux2", new JsonValue(payload.getAux2()));
        jsonObject.addChild("aux3", new JsonValue(payload.getAux3()));
        jsonObject.addChild("aux4", new JsonValue(payload.getAux4()));
        return jsonObject;
    }

    @Override
    public Msp104MotorPayload deserialize(JsonValue payload) {
        float roll = payload.get("roll").asFloat();
        float pitch = payload.get("pitch").asFloat();
        float yaw = payload.get("yaw").asFloat();
        float throttle = payload.get("throttle").asFloat();
        return new Msp104MotorPayload(roll, pitch, yaw, throttle);
    }
}
