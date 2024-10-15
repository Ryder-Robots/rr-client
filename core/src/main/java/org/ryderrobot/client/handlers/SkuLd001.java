package org.ryderrobot.client.handlers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.L298N;
import org.ryderrobot.models.hwmodel.OpCodes;
import org.ryderrobot.models.hwmodel.OpU1MaVcc;
import org.ryderrobot.models.hwmodel.l298.In;

import static org.ryderrobot.models.hwmodel.OpCodes.OP_U1_MA_VCC;

/**
 * implementation of land drone SKU-LD001
 */

public class SkuLd001 extends ControllerHandlerBase {

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
            new Action(OP_U1_MA_VCC),
            payload
        );

        // Add action to queue
        Json j = new Json();
        j.setOutputType(JsonWriter.OutputType.json);
        egress.push(j.toJson(operation));
    }
}
