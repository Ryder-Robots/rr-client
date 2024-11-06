package org.ryderrobot.client.handlers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.client.SocketWriter;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.hwmodel.OpU1DuelVcc;
import org.ryderrobot.models.hwmodel.l298.In;
import org.ryderrobot.models.hwmodel.payloads.LandDrone4Wheel;

public class SkuLd002 extends ControllerHandlerBase {
    private final SocketWriter socketWriter;
    private final Json j = new Json();
    LandDrone4Wheel payload;

    public SkuLd002(Drone drone) {
        j.setOutputType(JsonWriter.OutputType.json);
        socketWriter = new SocketWriter(drone);
    }

    @Override
    public void axisLeftY(float value, float xValue) {
        if (value > 0) {
            if (xValue > 0 || xValue == 0) {

                // Turn right
                payload = new LandDrone4Wheel(
                    value, value - xValue, In.EN_FORWARD,
                    value, value - xValue, In.EN_FORWARD
                );
            } else {
                payload = new LandDrone4Wheel(
                    value + xValue, value, In.EN_FORWARD,
                    value + xValue, value, In.EN_FORWARD
                );
            }
        } else {
            if (xValue > 0 || xValue == 0) {
                payload = new LandDrone4Wheel(
                    value * -1, (value * -1) - xValue, In.EN_BACKWARD,
                    value * -1, (value * -1) - xValue, In.EN_BACKWARD
                );
            } else {
                payload = new LandDrone4Wheel(
                    (value * -1) + xValue, value * -1, In.EN_BACKWARD,
                    (value * -1) + xValue, value * -1, In.EN_BACKWARD
                );
            }
        }
        // Add action to queue
        socketWriter.write(j.toJson(new OpU1DuelVcc(payload)));
    }
}
