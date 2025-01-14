package org.ryderrobot.drones;

import org.ryderrobot.models.controllers.ControllerRequest;
import org.ryderrobot.models.protocols.rrp.*;

public class DroneSdkLd001 extends AbstractDrone{
    final short EN_BACKWARD =  0b0101;
    final short EN_FORWARD =  0b1010;

    final short EN_LEFT = 0b0110;
    final short EN_RIGHT = 0b1001;

    final int OFFSET = 1000;
    final int MANFLIGHT_BMASK = 1 << 9;
    boolean mode_sent = false;


    // TODO: this is to be deprecated, for now just assume that once we have sent an event it is
    // handled.
    @Override
    public void handlerControllerEvent(ControllerRequest handler) throws RuntimeException {
        // check if mode is set here,
        //if ((getStatus().getFlag() &  MANFLIGHT_BMASK) != MANFLIGHT_BMASK) {
        if (!mode_sent) {
            RrpEvent<MspModePayload> modeEvent = new RrpEvent<>(
                RrpCommands.MSP_MODE,
                new MspModePayload(MspMode.CMODE_MANUAL_FLIGHT)
            );
            getSocketClient().send(modeEvent);
            mode_sent = true;
            return;
        }

        short in;
        float motor1, motor2, motor3 = 0, motor4 = 0;

        if (handler.getAxisYL() > 0.05) {
            in = EN_FORWARD;
            motor1 = handler.getAxisYL();
            motor2 = handler.getAxisYL();
        } else {
            in = EN_BACKWARD;
            motor1 = handler.getAxisYL() * -1;
            motor2 = handler.getAxisYL() * -1;
        }

        if (handler.getAxisXR() > 0.05) {
            in = EN_RIGHT;
        } else if (handler.getAxisXR() < -0.05) { //if (handler.getAxisXL() < 0) {
            in = EN_LEFT;
        }


        MspHbridgePayload hbridge = new MspHbridgePayload(in, motor1 * OFFSET, motor2 * OFFSET, motor3 * OFFSET, motor4 * OFFSET);
        RrpEvent<MspHbridgePayload> event = new RrpEvent<>(RrpCommands.MSP_SET_MOTOR_HBRIDGE, hbridge);
        getSocketClient().send(event);
    }
}
