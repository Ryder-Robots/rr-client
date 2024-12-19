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

public interface SocketClient {
    void connect(String host, int port);

    boolean isConnected();

    int available() throws RrpAbstractException;

    void send(RrpEvent event);

    RrpEvent recv() throws RrpAbstractException;

    public void close();
}
