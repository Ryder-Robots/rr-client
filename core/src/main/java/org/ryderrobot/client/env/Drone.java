package org.ryderrobot.client.env;

import org.ryderrobot.client.SocketClient;
import org.ryderrobot.models.DroneManifest;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Used to control a done. This class is used by all screens to communicate with the drone.
 */

public class Drone {


    private SocketClient socketClient;  // connection to the drone.
    private boolean connected = false;  // when true indicates that there is a connected drone.
    private DroneManifest manifest;     // description of the drone.

    private List<Action> egressQueue = new ArrayList<>();
    private List<Observer> ingressQueue = new ArrayList<>();

    private ReentrantLock egressLock = new ReentrantLock();
    private Condition egressAvailable = egressLock.newCondition();
    private ReentrantLock ingresLock = new ReentrantLock();
    private Condition ingressAvailable = ingresLock.newCondition();

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

    public void sendAction(Action action) {
        try {
            egressLock.lock();
            egressQueue.add(action);
        } finally {
            egressAvailable.signalAll();
            egressLock.unlock();
        }
    }

    public Action getAction() {
        Action action = null;
        if (!egressQueue.isEmpty()) {
            egressLock.lock();
            action = egressQueue.removeFirst();
            egressLock.unlock();
        }
        return action;
    }

    public int getSizeAction() {
        return egressQueue.size();
    }

    public Condition getEgressAvailable() {
        return egressAvailable;
    }
}
