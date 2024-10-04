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
import org.ryderrobot.models.ConnectionRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Create and maintain connection to drone. Connection accepts HTTP and WS,  both should have the connection
 * maintained.
 */
public class SocketClient {
    private Socket socket;
    private  InputStream  sockinfd;
    private  OutputStream sockoutfd;
    private final Json json = new Json();

    public SocketClient() {
        super();
        json.setOutputType(JsonWriter.OutputType.json);
    }

    public void init(String host, int port, String clientId, String atHash) {

        try {
              socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, new SocketHints());
              if (socket.isConnected()) {
                  sockinfd = socket.getInputStream();
                  sockoutfd = socket.getOutputStream();

                  // attempt to connect to drone
                  ConnectionRequest connectionRequest = new ConnectionRequest(clientId, atHash);
                  sockoutfd.write(json.toJson(connectionRequest).getBytes());
              }
        } catch (Exception ex) {
            throw new RuntimeException("network error", ex);
        }
    }


    void dispose() {
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
