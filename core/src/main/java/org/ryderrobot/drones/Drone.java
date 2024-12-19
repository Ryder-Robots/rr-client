package org.ryderrobot.drones;

import org.ryderrobot.models.controllers.ControllerRequest;
import org.ryderrobot.models.protocols.rrp.MspIdentPayload;
import org.ryderrobot.models.protocols.rrp.MspStatus;
import org.ryderrobot.net.SocketClient;

/**
 * Interface for drone.
 */
public interface Drone {

    /**
     * gets current socket client, if set.
     *
     * @return socket client
     */
    SocketClient getSocketClient();

    /**
     * perform any actions needed to close the drone.
     *
     */
    void dispose();

    /**
     * true if the drone is connected.
     *
     * @return true if the drone is connected.
     */
    boolean isConnected();

    /**
     * set to true if the drone is connected by an external entity.
     *
     * @param connected true or false.
     */
    void setIsConnected(boolean connected);

    /**
     * Returns payload indicated the drones identity, can be used with factory class
     * to get drone specific implementation.
     *
     * @return drone status
     */
    MspIdentPayload getIdentity();

    /**
     * current status of connected drone, can be used to determine if the drone is armed,
     * or if the drone has software or hardware faults.
     *
     * @return drone status.
     */
    MspStatus getStatus();

    /**
     * Called by the controller listener when an action has been taken.
     */
    void handlerControllerEvent(ControllerRequest handler);
}
