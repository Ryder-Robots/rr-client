package org.ryderrobot.client.handlers;

import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.OpCodes;

/**
 * implementation of land drone SKU-LD001
 */

public class SkuLd001 extends ControllerHandlerBase {

    @Override
    public void axisLeftY(float value) {
        if (value > 0) {
            egress.push(new Action(OpCodes.OP_U1_MA_VCC, value));
            egress.push(new Action(OpCodes.OP_U1_CMD, 0b0101));
        } else {
            egress.push(new Action(OpCodes.OP_U1_MA_VCC, value * -1));
            egress.push(new Action(OpCodes.OP_U1_CMD, 0b1010));
        }
    }
}
