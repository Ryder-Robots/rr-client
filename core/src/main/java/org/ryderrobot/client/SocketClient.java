package org.ryderrobot.client;

/*
 * Licence information goes here.
 */


import com.badlogic.gdx.utils.Json;
import com.github.czyzby.kiwi.util.common.Strings;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import org.codehaus.httpcache4j.uri.URIBuilder;
import org.ryderrobot.models.ConnectionRequest;

import java.net.URI;

/**
 * Create and maintain connection to drone. Connection accepts HTTP and WS,  both should have the connection
 * maintained.
 */
public class SocketClient {
    private static final String SCHEME = "ws";
    private  WebSocket socket;
    private Json json;

    private void init(String url, String clientId, String atHash) {
        socket = WebSockets.newSocket(url);
        socket.setSendGracefully(true);
        socket.addListener(new ConnectionListener());
        socket.connect();

        ConnectionRequest connectionRequest = new ConnectionRequest(
            atHash, clientId
        );
        socket.send(json.toJson(connectionRequest));
    }

    /**
     * HTTP REST request
     *
     * @param address to send request
     * @param port port
     * @param path path
     */
    public SocketClient(String address, int port, String path, String clientId, String atHash) {
        if (Strings.isEmpty(address)) {
            throw new RuntimeException("address is not set");
        }

        URIBuilder builder = URIBuilder.fromString(address);
        URI uri = builder.withScheme(SCHEME).withHost(address).withPort(port).withPath(path).toURI();
        init(uri.toASCIIString(), clientId, atHash);
    }

    /**
     * Websocket request
     */
    public SocketClient(String address, int port, String clientId, String atHash) {
        if (Strings.isEmpty(address)) {
            throw new RuntimeException("address is not set");
        }
        init(WebSockets.toWebSocketUrl(address, port), clientId, atHash);
    }

    void dispose() {
        socket.close();
    }
}
