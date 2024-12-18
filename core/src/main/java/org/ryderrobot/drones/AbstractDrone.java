package org.ryderrobot.drones;

import org.ryderrobot.net.SocketClient;

public abstract class AbstractDrone implements Drone {

    @Override
    public SocketClient getSocketClient() {
        return socketClient;
    }

    @Override
    public void dispose() {
        socketClient.close();
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void setIsConnected(boolean connected) {
        this.connected = connected;
    }

    private SocketClient socketClient = new SocketClient();
    private boolean connected = false;
}
