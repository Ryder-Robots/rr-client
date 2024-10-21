package org.ryderrobot.client.handlers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.client.SocketWriter;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.payloads.L298N;
import org.ryderrobot.models.hwmodel.OpU1MaVcc;
import org.ryderrobot.models.hwmodel.l298.In;

import static org.ryderrobot.models.hwmodel.OpCodes.OP_U1_MA_VCC;

/**
 * implementation of land drone SKU-LD001
 */

public class SkuLd001 extends ControllerHandlerBase {
    private SocketWriter socketWriter;

    public SkuLd001(Drone drone) {
        socketWriter = new SocketWriter(drone);
    }

    /**
     *  invoked each time the left controller is moved on the Y axis.
     *
     * @param value position of left controller axis.
     */
    @Override
    public void axisLeftY(float value) {
        L298N payload;

        if (value > 0) {
            payload = new L298N(value, value, In.EN_FORWARD);
        } else {
            payload = new L298N(value * -1, value * -1, In.EN_BACKWARD);
        }

        OpU1MaVcc operation = new OpU1MaVcc(
            payload
        );

        // Add action to queue
        Json j = new Json();
        j.setOutputType(JsonWriter.OutputType.json);
        socketWriter.write(j.toJson(operation));
        //egress.push(j.toJson(operation));
    }
}
