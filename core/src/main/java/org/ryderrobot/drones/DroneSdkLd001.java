package org.ryderrobot.drones;

import org.ryderrobot.models.controllers.ControllerRequest;
import org.ryderrobot.models.protocols.rrp.*;

public class DroneSdkLd001 extends AbstractDrone{
    final short EN_BACKWARD =  0b0101;
    final short EN_FORWARD =  0b1010;
    final int OFFSET = 1000;
    final int MANFLIGHT_BMASK = 1 << 9;


    @Override
    public void handlerControllerEvent(ControllerRequest handler) {
        // check if mode is set here,
        if ((getStatus().getFlag() &  MANFLIGHT_BMASK) != MANFLIGHT_BMASK) {
            RrpEvent<MspModePayload> modeEvent = new RrpEvent<>(
                RrpCommands.MSP_MODE,
                new MspModePayload(MspMode.CMODE_MANUAL_FLIGHT)
            );
            getSocketClient().send(modeEvent);
            return;
        }

        short in;
        float motor1, motor2, motor3 = 0, motor4 = 0;

        if (handler.getAxisYL() > 0) {
            in = EN_FORWARD;
            if (handler.getAxisXL() > 0 || handler.getAxisXL() == 0) {
                motor1 = handler.getAxisYL();
                motor2 = handler.getAxisYL() - handler.getAxisXL();
            } else {
                motor1 = handler.getAxisYL() + handler.getAxisXL();
                motor2 = handler.getAxisYL();
            }
        } else {
            in = EN_BACKWARD;
            if (handler.getAxisXL() > 0 || handler.getAxisXL() == 0) {
                motor1 = handler.getAxisYL() * -1;
                motor2 = (handler.getAxisYL() * -1) - handler.getAxisXL();
            } else {
                motor1 = (handler.getAxisYL() * -1) + handler.getAxisXL();
                motor2 = handler.getAxisYL() * -1;
            }
        }


        MspHbridgePayload hbridge = new MspHbridgePayload(in, motor1 * OFFSET, motor2 * OFFSET, motor3 * OFFSET, motor4 * OFFSET);
        RrpEvent<MspHbridgePayload> event = new RrpEvent<>(RrpCommands.MSP_SET_MOTOR_HBRIDGE, hbridge);
        getSocketClient().send(event);
    }
}
