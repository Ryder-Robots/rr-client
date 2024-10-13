package org.ryderrobot.client;

/*
 * Licence information goes here.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.StringBuilder;
import org.ryderrobot.Constants;
import org.ryderrobot.models.ConnectionRequest;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.DroneManifest;

import java.io.*;

/**
 * Create and maintain connection to drone. Connection accepts HTTP and WS,  both should have the connection
 * maintained.
 */
public class SocketClient  {
    private Socket socket;
    private InputStream sockinfd;
    private OutputStream sockoutfd;
    private final Json json = new Json();

    public SocketClient() {
        super();
        json.setOutputType(JsonWriter.OutputType.json);
    }

    public void egress(@Null Object object) {
        try {
            sockoutfd.write(json.toJson(object).getBytes());
            sockoutfd.flush();
        } catch (IOException ex) {
            //TODO: handle exception.
        }
    }

    private DroneManifest ingressInternal() {
        DroneManifest object = null;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(sockinfd));
            StringBuilder sb = new StringBuilder();

            if (!buffer.ready()) {
                for (int i = 0; i < Constants.TIMEOUT; i++) {
                    Thread.sleep(10);
                }
            }

            if (!buffer.ready()) {
                throw new RuntimeException("connection timed out");
            }

            while (buffer.ready()) {
                sb.append((char) buffer.read());
            }
            object = new Json().fromJson(DroneManifest.class, sb.toString());
        } catch (IOException ex) {
            throw new RuntimeException("invalid manifest returned: " + ex.getMessage());
        } catch (InterruptedException ex) {
            throw new RuntimeException("connection interrupted: " + ex.getMessage());
        }
        return object;
    }




    public void init(String host, int port, String clientId, String atHash, Drone drone) {
        DroneManifest manifest = null;
        try {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, new SocketHints());
            if (socket.isConnected()) {
                sockinfd = socket.getInputStream();
                sockoutfd = socket.getOutputStream();

                // attempt to connect to drone
                egress(new ConnectionRequest(clientId, atHash));
                manifest = ingressInternal();
                drone.setManifest(manifest);
            }
        } catch (Exception ex) {
            throw new RuntimeException("network error", ex);
        }
    }

    public InputStream getSockinfd() {
        return sockinfd;
    }

    public OutputStream getSockoutfd() {
        return sockoutfd;
    }


    public void dispose() {
        try {
            if (socket.isConnected()) {
                sockinfd.close();
                sockoutfd.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("network error", ex);
        }
    }
}
