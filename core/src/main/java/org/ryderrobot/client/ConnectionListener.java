package org.ryderrobot.client;

import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class ConnectionListener implements WebSocketListener {
    @Override
    public boolean onOpen(WebSocket webSocket) {
        return false;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int i, String s) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String s) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] bytes) {
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable throwable) {
        return false;
    }
}
