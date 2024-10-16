package org.ryderrobot.env;

import com.badlogic.gdx.utils.Json;
import org.ryderrobot.client.SocketClient;
import org.ryderrobot.client.handlers.ControllerHandler;
import org.ryderrobot.client.handlers.HandlerFactory;
import org.ryderrobot.models.DroneManifest;

import java.util.Optional;

/**
 * Used to control a done. This class is used by all screens to communicate with the drone.
 */

public class Drone {


    private SocketClient socketClient;  // connection to the drone.
    private boolean connected = false;  // when true indicates that there is a connected drone.
    private DroneManifest manifest;     // description of the drone.
    private ControllerHandler handler;

    private final Queue<String> egress = new Queue<>();
    private final Queue<String> ingres = new Queue<>();

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public void setManifest(DroneManifest manifest) {
        if (Optional.ofNullable(manifest).isEmpty()) {
            throw new RuntimeException("did not receive manifest from drone.");
        }

        connected = true;
        handler = HandlerFactory.getHandler(manifest, ingres, egress);
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

    public Queue<String> getEgress() {
        return egress;
    }

    public Queue<String> getIngres() {
        return ingres;
    }

    public ControllerHandler getHandler() {
        return handler;
    }
}
