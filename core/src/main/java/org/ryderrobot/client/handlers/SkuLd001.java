package org.ryderrobot.client.handlers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.client.SocketWriter;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.hwmodel.payloads.L298N;
import org.ryderrobot.models.hwmodel.OpU1MaVcc;
import org.ryderrobot.models.hwmodel.l298.In;
/**
 * implementation of land drone SKU-LD001
 */

public class SkuLd001 extends ControllerHandlerBase {
    private final SocketWriter socketWriter;
    private final Json j = new Json();

    L298N payload;

    public SkuLd001(Drone drone) {
        socketWriter = new SocketWriter(drone);
        j.setOutputType(JsonWriter.OutputType.json);
    }

    /**
     *  invoked each time the left controller is moved on the Y axis.
     *
     * @param value position of left controller axis.
     */

    /*
     * for xValue a negative means that we the stick is facing left, positive right.
     */
    @Override
    public void axisLeftY(float value, float xValue) {
        if (value > 0) {
            if (xValue > 0 || xValue == 0) {
                payload = new L298N(value, value - xValue, In.EN_FORWARD);
            } else {
                payload = new L298N(value + xValue, value, In.EN_FORWARD);
            }
        } else {
            if (xValue > 0 || xValue == 0) {
                payload = new L298N(value * -1, (value * -1) - xValue, In.EN_BACKWARD);
            } else {
                payload = new L298N((value * -1) + xValue, value * -1, In.EN_BACKWARD);
            }
        }
        // Add action to queue
        socketWriter.write(j.toJson(new OpU1MaVcc(payload)));
        //egress.push(j.toJson(operation));
    }

    @Override
    public void axisRightX(float value, float yValue) {
        if (value > 0) {
            payload = new L298N(value, value, In.EN_RIGHT);
        } else {
            payload =  new L298N(value * -1, value * -1, In.EN_LEFT);
        }
        socketWriter.write(j.toJson(new OpU1MaVcc(payload)));
    }
}
