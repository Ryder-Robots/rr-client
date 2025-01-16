package org.ryderrobot.drones;

import org.ryderrobot.exceptions.RrpInterruptException;
import org.ryderrobot.models.controllers.ControllerRequest;
import org.ryderrobot.models.protocols.rrp.*;

public class DroneSdkLd001 extends AbstractDrone{
    boolean mode_sent = false;


    /**
     * TODO: this should become a part of AbstractDrone, it should work for other drones.
     * also needs roll and pitch added.
     *
     * @param handler
     * @throws RuntimeException
     */
    @Override
    public void handlerControllerEvent(ControllerRequest handler) throws RuntimeException {
        if (!mode_sent) {
            RrpEvent<MspModePayload> modeEvent = new RrpEvent<>(
                RrpCommands.MSP_MODE,
                new MspModePayload(MspMode.CMODE_MANUAL_FLIGHT)
            );
            getSocketClient().send(modeEvent);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RrpInterruptException(e);
            }
            mode_sent = true;
            return;
        }

        float yaw = handler.getAxisXL();
        float throttle = (handler.getAxisYL() < 0) ? handler.getAxisYL() * -1: handler.getAxisYL();

        if (throttle  > 0 && throttle < 0.05) {
            throttle = 0;
        }
        Msp104MotorPayload payload = new Msp104MotorPayload(0, 0, yaw, throttle);
        RrpEvent<Msp104MotorPayload> event = new RrpEvent<>(RrpCommands.MSP_MOTOR, payload);
        getSocketClient().send(event);
    }
}
