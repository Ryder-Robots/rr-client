package org.ryderrobot.drones;

import org.ryderrobot.models.controllers.ControllerRequest;

/**
 * All drones start as virtual until connection is made, then
 * depending on the MSP_IDENT will change to the correct drone.
 */
public class VirtualDrone extends AbstractDrone {
    @Override
    public void handlerControllerEvent(ControllerRequest handler) {

        // Must be the last command called.
        handler.reset();
    }
}
