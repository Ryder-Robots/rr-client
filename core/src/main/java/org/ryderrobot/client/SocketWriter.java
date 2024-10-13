package org.ryderrobot.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.hwmodel.Action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

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
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        while(drone.isConnected()) {
            try {
                if (drone.getEgress().size() > 0) {

                    Optional<Action> action = drone.getEgress().pop();
                    if (action.isPresent()) {
                        OutputStream sockOutFd = drone.getSocketClient().getSockoutfd();
                        sockOutFd.write(json.toJson(action.get()).getBytes());
                        sockOutFd.flush();
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException("lost connection with drone");
            }
        }

    }
}
