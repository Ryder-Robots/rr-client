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
import org.ryderrobot.models.ConnectionRequest;
import org.ryderrobot.client.env.Drone;
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

    private <T> T ingressInternal(Class<T> clazz) {
        T object = null;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(sockinfd));
            StringBuilder sb = new StringBuilder();

            while (buffer.ready()) {
                sb.append((char) buffer.read());
            }
            object = new Json().fromJson(clazz, sb.toString());
        } catch (IOException ex) {
            //TODO: handle exception.
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
                manifest = ingressInternal(DroneManifest.class);

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
