package org.ryderrobot.net;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ryderrobot.net.SocketClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class TestSocketClient {

    @Test
    public void shouldCreateConnection() {
        SocketClient socketClient = new TcpSocketClient();
        socketClient.connect("192.168.1.35", 8081);

        assertTrue(socketClient.isConnected());
        socketClient.close();
    }
}
