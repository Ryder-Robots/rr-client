package org.ryderrobot.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.net.SocketHints;
import org.ryderrobot.constants.Constants;
import org.ryderrobot.exceptions.RrpAbstractException;
import org.ryderrobot.exceptions.RrpInterruptException;
import org.ryderrobot.exceptions.RrpIoException;
import org.ryderrobot.models.protocols.rrp.RrpCommands;
import org.ryderrobot.models.protocols.rrp.RrpCurator;
import org.ryderrobot.models.protocols.rrp.RrpEvent;

import static org.ryderrobot.constants.Constants.EOR;

public class SocketClient {
    public void connect(String host, int port) {
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, new SocketHints());
        if (socket.isConnected()) {
            sockinfd = socket.getInputStream();
            sockoutfd = socket.getOutputStream();
        }
    }

    public boolean isConnected() {
        if (socket != null) {
            return socket.isConnected();
        }
        return false;
    }

    public int available() throws RrpAbstractException {
        int available = 0;

        if (isConnected()) {
            try {
                available = sockinfd.available();
            } catch (IOException ex) {
                throw new RrpIoException(ex);
            }
        }

        return available;
    }

    public void send(RrpEvent event) {
        try {
            String msg = curator.toJson(event);
            msg = msg + EOR;
            OutputStream sockOutFd = sockoutfd;
            sockOutFd.write(msg.getBytes());
            sockOutFd.flush();
        } catch (IOException ex) {
            throw new RuntimeException("lost connection with drone");
        }
    }

    public RrpEvent recv() throws RrpAbstractException {
        RrpEvent event = new RrpEvent(RrpCommands.MSP_NONE);
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(sockinfd));
            StringBuilder sb = new StringBuilder();

            if (!buffer.ready()) {
                for (int i = 0; i < Constants.TIMEOUT; i++) {
                    Thread.sleep(Constants.SLEEP_TIME);
                }

                if (!buffer.ready()) {
                    throw new RuntimeException("connection timed out");
                }
                char c = '0';
                while (c != Constants.EOR) {
                    c = (char) (buffer.read() + '0');
                    if (c != EOR) {
                        sb.append(c);
                    }
                }

                event = curator.curate(sb.toString());
            }
        } catch (InterruptedException ex) {
            throw new RrpInterruptException(ex);
        } catch (IOException ex) {
            throw new RrpIoException(ex);
        }
        return event;
    }

    public void close() {
        try {
            sockinfd.close();
            sockoutfd.close();
            socket.dispose();
        } catch (IOException ex) {
            throw new RrpIoException(ex);
        }
    }

    private InputStream sockinfd;
    private OutputStream sockoutfd;
    private Socket socket = null;
    private RrpCurator curator = new RrpCurator();
}
