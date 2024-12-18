package org.ryderrobot.drones;

import org.ryderrobot.net.SocketClient;

/**
 * Interface for drone.
 */
public interface Drone {
    SocketClient getSocketClient();

    void dispose();

    boolean isConnected();

    void setIsConnected(boolean connected);
}
