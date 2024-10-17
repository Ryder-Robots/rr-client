package org.ryderrobot.client;

/*
 * Licence information goes here.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import org.ryderrobot.Constants;
import org.ryderrobot.models.ConnectionRequest;
import org.ryderrobot.env.Drone;
import org.ryderrobot.models.DroneManifest;
import org.ryderrobot.models.hwmodel.OpDisconnect;

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

    /**
     * create new socket client instance.
     */
    public SocketClient() {
        super();
        json.setOutputType(JsonWriter.OutputType.json);
    }

    /**
     * write to socket, socket must have a connection first.
     *
     * @param object to write
     */
    public void egress(@Null Object object) {
        try {
            sockoutfd.write(json.toJson(object).getBytes());
            sockoutfd.flush();
        } catch (IOException ex) {
            throw new RuntimeException("could not communicate with drone: " + ex.getMessage());
        }
    }

    /**
     * get manifest from drone.
     *
     * @return drone manifest
     */
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

    /**
     * initilize connection to drone.
     *
     * @param host network identifier of drone
     * @param port TCP connection
     * @param clientId client Identifier for authentication
     * @param atHash client passphrase
     * @param drone drone object
     */
    public void init(String host, int port, String clientId, String atHash, Drone drone) {
        DroneManifest manifest;
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
        } catch (SerializationException ex) {
            // attempt to disconnect from drone.
            dispose();
            throw new RuntimeException("handshake error", ex);
        } catch (Exception ex) {
            throw new RuntimeException("network error", ex);
        }
    }

    /**
     * input stream, receives observations from drone.
     *
     * @return drone input stream
     */
    public InputStream getSockinfd() {
        return sockinfd;
    }

    /**
     * output stream for drone.
     *
     * @return drone output stream
     */
    public OutputStream getSockoutfd() {
        return sockoutfd;
    }

    /**
     * called to disconnect the drone.
     */
    public void dispose() {
        try {
            if (socket.isConnected()) {
                egress(new OpDisconnect());
                sockinfd.close();
                sockoutfd.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("network error", ex);
        }
    }

    /**
     * disconnect from drone.
     *
     */
    public void disconnect() {
        dispose();
    }
}
