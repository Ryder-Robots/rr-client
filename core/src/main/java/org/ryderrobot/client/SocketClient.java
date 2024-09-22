package org.ryderrobot.client;

/*
 * Licence information goes here.
 */


import com.github.czyzby.kiwi.util.common.Strings;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.net.URI;

/**
 * Create and maintain connection to drone. Connection accepts HTTP and WS,  both should have the connection
 * maintained.
 */
public class SocketClient {
    private static final String SCHEME = "http";
    private  WebSocket socket;

    private void init(String url) {
        socket = WebSockets.newSocket(url);
        socket.setSendGracefully(true);
        socket.addListener(new ConnectionListener());
        socket.connect();
        socket.send("hello world");
    }

    /**
     * HTTP REST request
     *
     * @param address to send request
     * @param port port
     * @param path path
     */
    public SocketClient(String address, int port, String path) {
        if (Strings.isEmpty(address)) {
            throw new RuntimeException("address is not set");
        }

        URIBuilder builder = URIBuilder.fromString(address);
        URI uri = builder.withScheme(SCHEME).withHost(address).withPort(port).withPath(path).toURI();
        init(uri.toASCIIString());
    }

    /**
     * Websocket request
     */
    public SocketClient(String address, int port) {
        if (Strings.isEmpty(address)) {
            throw new RuntimeException("address is not set");
        }
        init(WebSockets.toWebSocketUrl(address, port));
    }

    void dispose() {
        socket.close();
    }
}
