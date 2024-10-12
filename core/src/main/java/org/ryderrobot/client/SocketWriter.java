package org.ryderrobot.client;

import com.badlogic.gdx.utils.Json;
import org.ryderrobot.client.env.Drone;
import org.ryderrobot.models.hwmodel.Action;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Gets called when actions are to be sent to the drone.
 */

public class SocketWriter implements Runnable{
    private final Drone drone;

    public SocketWriter(Drone drone) {
        this.drone = drone;
    }

    @Override
    public void run() {
        while(drone.isConnected()) {
            try {
                drone.getEgressAvailable().await();
                Action action = drone.getAction();
                if (drone.getSizeAction() > 0) {
                    OutputStream sockoutfd = drone.getSocketClient().getSockoutfd();

                    sockoutfd.write((new Json()).toJson(action).getBytes());
                    sockoutfd.flush();
                }
            } catch (InterruptedException interruptedException) {
                // TODO: ignore for now. these probaly want to be put on a failed request queue or something
            } catch (IOException ex) {
                // TODO: handle or put on a failed request queue
            }
        }

    }
}
