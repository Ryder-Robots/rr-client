package org.ryderrobot.models;

import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import org.ryderrobot.client.SocketClient;

/**
 * Used to control a done. This class is used by all screens to communicate with the drone.
 */

public class Drone {


    private SocketClient socketClient;  // connection to the drone.
    private boolean connected = false;  // when true indicates that there is a connected drone.
    private DroneManifest manifest;     // description of the drone.

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public void setManifest(DroneManifest manifest) {
        connected = true;
        this.manifest = manifest;
    }

    public DroneManifest getManifest() {
        return manifest;
    }

    public boolean isConnected() {
        return connected;
    }

    public void dispose() {
        socketClient.dispose();
    }
}
