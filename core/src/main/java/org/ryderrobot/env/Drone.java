package org.ryderrobot.env;

import org.ryderrobot.client.SocketClient;
import org.ryderrobot.models.DroneManifest;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.Observer;

/**
 * Used to control a done. This class is used by all screens to communicate with the drone.
 */

public class Drone {


    private SocketClient socketClient;  // connection to the drone.
    private boolean connected = false;  // when true indicates that there is a connected drone.
    private DroneManifest manifest;     // description of the drone.

    private final Queue<Action> egress = new Queue();
    private final Queue<Observer> ingres = new Queue();

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
        connected = false;
    }

    public Queue<Action> getEgress() {
        return egress;
    }

    public Queue<Observer> getIngres() {
        return ingres;
    }
}
