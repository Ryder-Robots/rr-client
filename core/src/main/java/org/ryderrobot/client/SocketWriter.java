package org.ryderrobot.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.env.Drone;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Gets called when actions are to be sent to the drone.
 */

public class SocketWriter implements Runnable {
    private final Drone drone;

    public SocketWriter(Drone drone) {
        this.drone = drone;
    }

    public void write(String msg) {
        final char EOR = 0x1E;
        try {
            msg = msg + EOR;
            OutputStream sockOutFd = drone.getSocketClient().getSockoutfd();
            sockOutFd.write(msg.getBytes());
            sockOutFd.flush();
        } catch (IOException ex) {
            throw new RuntimeException("lost connection with drone");
        }
    }

    @Override
    public void run() {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        while(drone.isConnected()) {
                if (drone.getEgress().size() > 0) {
                    Optional<String> action = drone.getEgress().pop();
                    action.ifPresent(this::write);
                }
        }
    }
}
